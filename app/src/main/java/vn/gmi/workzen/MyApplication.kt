package vn.gmi.workzen

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import dagger.hilt.android.HiltAndroidApp
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.utils.MySharedPreferences

class MyApplication : Application (){

    companion object {
        lateinit var instance: MyApplication
            private set

        const val CHANNEL_NORMAL = "notification_core"
        const val CHANNEL_WORK = "work_schedule"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        createNotificationChannels(this)
        MySharedPreferences.init(this)
        ApiService.instance.initBaseUrl(BuildConfig.API_BASE_URL)
    }


    private fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(CHANNEL_NORMAL, getString(R.string.channel_name), NotificationManager.IMPORTANCE_HIGH).apply {
                    description = getString(R.string.channel_description)
                    enableVibration(true)
                    vibrationPattern = longArrayOf(100, 200, 300)
                },
                NotificationChannel(CHANNEL_WORK, "Lịch công việc", NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = "Thông báo nhắc việc hằng ngày"
                    enableLights(true)
                }
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            channels.forEach { manager.createNotificationChannel(it) }
        }
    }

}