package vn.gmi.workzen.services.socket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketService (private val webSocketListener: AppWebSocketListener) : WebSocketListener(){

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.w(TAG,"Websocket is closed")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.w(TAG,"Websocket is closing")
    }

    override fun onFailure(
        webSocket: WebSocket,
        t: Throwable,
        response: Response?
    ) {
        super.onFailure(webSocket, t, response)
        Log.e(TAG,"Websocket connected failure")
        t.printStackTrace()
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d(TAG,"Websocket receive message: $text")
        webSocketListener.onReceiveMessage(text)

    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d(TAG,"Websocket is connected")
    }


    companion object{
        private val TAG = WebSocketService::class.java.name
    }
}