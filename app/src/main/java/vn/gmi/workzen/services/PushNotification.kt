package vn.gmi.workzen.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import vn.gmi.workzen.ui.main.MainActivity
import vn.gmi.workzen.MyApplication
import vn.gmi.workzen.R
import vn.gmi.workzen.domain.entity.notification.NotifyType
import vn.gmi.workzen.ui.chat.conversation.ChatActivity
import vn.gmi.workzen.ui.notification.NotificationActivity
import vn.gmi.workzen.utils.IntentData


object PushNotification {

    fun notificationHandleClick(title: String, message: String, dataJson: String, type: String){
        var activity = when(type){
            NotifyType.NOTIFICATION.name -> NotificationActivity::class.java
            NotifyType.WARNING_LOGIN.name -> MainActivity::class.java
            NotifyType.CHAT.name -> ChatActivity::class.java
            else -> MainActivity::class.java
        }
        // Intent mở activity khi click thông báo
        val intent = Intent(MyApplication.instance, activity).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(IntentData.KEY_DATA_FROM_FCM, dataJson)
        }
        val pendingIntent = PendingIntent.getActivity(MyApplication.instance, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        sendSimpleNotification(title,message)
    }

    fun sendSimpleNotification(title: String, message: String, channelId:String = MyApplication.CHANNEL_NORMAL){
        val notificationManager =
            MyApplication.instance.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(MyApplication.instance.applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder)
    }
}