package vn.gmi.workzen.manager.schedule

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import vn.gmi.workzen.services.PushNotification

class DailyReminderWorker(context: Context,
                          workerParams: WorkerParameters
): Worker(context, workerParams) {

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Nhắc nhở"
        val message = inputData.getString("message") ?: "Đã đến giờ!"
        val hour = inputData.getInt("hour", 0)
        val minute = inputData.getInt("minute", 0)

        PushNotification.sendSimpleNotification(title, message)
        ReminderScheduler.scheduleReminder(applicationContext, hour, minute, title, message)

        return Result.success()
    }
}