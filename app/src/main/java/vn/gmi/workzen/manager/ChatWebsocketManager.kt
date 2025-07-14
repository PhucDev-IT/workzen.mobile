package vn.gmi.workzen.manager

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import vn.gmi.workzen.BuildConfig
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.data.models.request.conversation.ChatMessage
import vn.gmi.workzen.data.models.response.conversation.MessageResponseModel
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.domain.entity.enums.WebSocketTopic
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.services.socket.AppWebSocketListener
import vn.gmi.workzen.utils.MySharedPreferences
import java.lang.ref.WeakReference

object ChatWebsocketManager {

    private val handler = Handler(Looper.getMainLooper())
    private const val reconnectDelayMillis = 300000L

    private val TAG = ChatWebsocketManager::class.java.simpleName
    private const val SOCKET_URL = BuildConfig.WEB_SOCKET_URL

    private var listenerRef: WeakReference<AppWebSocketListener>? = null

    private val stompClient: StompClient by lazy {
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, SOCKET_URL)
    }

    @SuppressLint("CheckResult")
    fun connect(onConnected: (() -> Unit)? = null, onError: ((Throwable) -> Unit)? = null) {
        if (stompClient.isConnected) return
        val token = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_BEAR_ACCESS_TOKEN)
        val headers = listOf(
            StompHeader("Authorization", "Bearer $token")
        )
        stompClient.connect(headers)

        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.i(TAG, "🔌 STOMP connected")
                    onConnected?.invoke()

                    subscribe(WebSocketTopic.MESSAGE_ALL){decodeMessage(it)}
                }

                LifecycleEvent.Type.ERROR -> {
                    Log.e(TAG, "❌ STOMP connection error", lifecycleEvent.exception)
                    onError?.invoke(lifecycleEvent.exception)
                    scheduleReconnect()
                }

                LifecycleEvent.Type.CLOSED -> {
                    Log.w(TAG, "🛑 STOMP disconnected")
                    scheduleReconnect()
                }

                else -> Unit
            }
        }
    }

    fun disconnect() {
        stompClient.disconnect()
    }

    @SuppressLint("CheckResult")
    fun send(destination: String, payload: String) {
        stompClient.send(destination, payload).subscribe({
            Log.d(TAG, "✅ Sent to $destination: $payload")
        }, {
            Log.e(TAG, "❌ Send failed", it)
        })
    }

    @SuppressLint("CheckResult")
    fun subscribe(topic: String, onMessage: (String) -> Unit) {
        stompClient.topic(topic).subscribe { stompMessage ->
            Log.d(TAG, "📩 Received from $topic: ${stompMessage.payload}")
            onMessage(stompMessage.payload)
        }
    }

    private fun scheduleReconnect() {
        Log.i(TAG, "⏳ Scheduling reconnect in $reconnectDelayMillis ms...")
        handler.postDelayed({
            if (!stompClient.isConnected) {
                Log.i(TAG, "🔁 Attempting reconnect...")
                connect()
            }
        }, reconnectDelayMillis)
    }


    fun setListener(listener: AppWebSocketListener?) {
        listenerRef = listener?.let { WeakReference(it) }
    }


    fun sendMessage(message: ChatMessage){
        val entity = message.mapToEntity()

        val json = Gson().toJson(message)
        send("/app/chat.send", json)
        Log.d(TAG,"✅ Sent to /app/chat.send: $json")
        CoroutineScope(Dispatchers.IO).launch {
            RealmProvider.realm.write {
                copyToRealm(entity, UpdatePolicy.ALL)
            }
        }
    }

    private fun decodeMessage(message: String){
        try{

            val response = ApiService.instance.GSON.fromJson<MessageResponseModel>(message, MessageResponseModel::class.java)
            val entity = response.mapToEntity()
            RealmProvider.realm.writeBlocking {
                copyToRealm(entity, UpdatePolicy.ALL)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}
