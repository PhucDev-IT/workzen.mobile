package vn.gmi.workzen.services

import android.app.ActivityManager
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.utils.MySharedPreferences

class MyFirebaseService : FirebaseMessagingService() {

    companion object{
        private val TAG = "MyFirebaseService"

        fun registerTopic(topic: String) {
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
        }
    }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.data["title"] ?: "Thông báo"
        val body = message.data["body"] ?: ""
        val dataJson = message.data["data"]
        val type = message.data["type"]
        val isClick = message.data["isClick"] as Boolean

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