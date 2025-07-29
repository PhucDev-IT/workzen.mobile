package vn.gmi.workzen.services

import android.app.ActivityManager
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.models.response.conversation.MessageResponseModel
import vn.gmi.workzen.domain.entity.notification.NotifyType
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.utils.MySharedPreferences

class MyFirebaseService : FirebaseMessagingService() {

    companion object{
        private val TAG = "MyFirebaseService"

        fun registerTopic(topic: String) {
            val currentTopics = MySharedPreferences.getStringValues(SharedPreferenceKey.FCM_TOPICS).orEmpty()
            val topicList = currentTopics.split(",").filter { it.isNotBlank() }.toMutableList()

            if (!topicList.contains(topic)) {
                FirebaseMessaging.getInstance().subscribeToTopic(topic)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            topicList.add(topic)
                            MySharedPreferences.setStringValue(SharedPreferenceKey.FCM_TOPICS, topicList.joinToString(","))
                        }
                    }
            }
        }



        fun unsubscribeFromTopic() {
            val topicString = MySharedPreferences.getStringValues(SharedPreferenceKey.FCM_TOPICS).orEmpty()
            val topics = topicString.split(",").filter { it.isNotBlank() }.toMutableList()

            topics.forEach { topic ->
                FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            topics.remove(topic)
                            MySharedPreferences.setStringValue(SharedPreferenceKey.FCM_TOPICS, topics.joinToString(","))
                        }
                    }
            }
        }

    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "onMessageReceived: ${message.from}")

        var body = message.data["title"] ?: ""
        val title = message.data["title"] ?: "Thông báo"
        val dataJson = message.data["data"]
        val type = message.data["type"]
        val isClick = message.data["isClick"]?.toBoolean() == true

        var senderId:String? = null
        try {
            val notificationModel = ApiService.instance.GSON.fromJson<MessageResponseModel>(message.data["data"],
                MessageResponseModel::class.java)
            senderId  = notificationModel.sender?.id
            Log.d(TAG, "onMessageReceived: $notificationModel")
            if(body.isNotBlank()){
                body = "${notificationModel.sender?.name}: $body"
            }
        }catch (e: Exception){}

        if(type == NotifyType.CHAT.name && senderId == MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)){
            return
        }

        if(isClick){
            PushNotification.notificationHandleClick(title,body, dataJson.toString(),
                type.toString()
            )
        }else{
           PushNotification.sendSimpleNotification(title,body)
        }

    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
        MySharedPreferences.setStringValue(SharedPreferenceKey.FCM_TOKEN, token)
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().subscribeToTopic("all")
    }


    // Hàm kiểm tra app đang foreground
    private fun isAppInForeground(): Boolean {
        val appProcessInfo = ActivityManager.RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(appProcessInfo)
        return appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
    }


}