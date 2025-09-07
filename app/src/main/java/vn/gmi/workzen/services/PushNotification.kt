package vn.gmi.workzen.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import vn.gmi.workzen.ui.main.MainActivity
import vn.gmi.workzen.MyApplication
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.data.models.response.conversation.MessageResponseModel
import vn.gmi.workzen.domain.entity.notification.NotifyType
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.ui.chat.conversation.ChatActivity
import vn.gmi.workzen.ui.chat.message.MessengerActivity
import vn.gmi.workzen.ui.notification.NotificationActivity
import vn.gmi.workzen.utils.IntentData

object PushNotification {

    fun notificationHandleClick(title: String, message: String, dataJson: String, type: String) {
        Log.d("PushNotification", "type: $type")

        val current = BaseActivity.currentActivity
        val activity = when (type) {
            NotifyType.NOTIFICATION.name -> NotificationActivity::class.java
            NotifyType.WARNING_LOGIN.name -> MainActivity::class.java
            NotifyType.CHAT.name -> MessengerActivity::class.java
            else -> MainActivity::class.java
        }

        // Nếu đang mở đúng Activity thì không cần mở lại
        if (current != null && current::class.java == activity) {
            Log.d("PushNotification", "Đang mở activity $activity, không mở lại.")
            return
        }

        // Intent mở activity khi click thông báo
        val intent = Intent(MyApplication.instance, activity).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(IntentData.KEY_DATA_FROM_FCM, dataJson)
        }

        val pendingIntent = PendingIntent.getActivity(
            MyApplication.instance,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        sendSimpleNotification(title, message, MyApplication.CHANNEL_NORMAL, pendingIntent)
    }

    fun sendSimpleNotification(
        title: String,
        message: String,
        channelId: String = MyApplication.CHANNEL_NORMAL,
        pendingIntent: PendingIntent? = null
    ) {
        val context = MyApplication.instance.applicationContext
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)

        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }
}
