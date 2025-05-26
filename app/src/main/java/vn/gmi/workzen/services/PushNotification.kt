package vn.gmi.workzen.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import vn.gmi.workzen.MyApplication
import vn.gmi.workzen.R


object PushNotification {


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