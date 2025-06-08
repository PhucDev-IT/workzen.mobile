package vn.gmi.workzen.manager.schedule

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.utils.FormatUtils
import vn.gmi.workzen.utils.MySharedPreferences
import java.util.Calendar
import java.util.concurrent.TimeUnit

object ReminderScheduler {
    fun scheduleAllIfNeeded(context: Context) {
        val prefs = MySharedPreferences.getBooleanValue(SharedPreferenceKey.REMINDER_SCHEDULE)
        if (!prefs) {
            scheduleAll(context)
            MySharedPreferences.setBooleanValue(SharedPreferenceKey.REMINDER_SCHEDULE, true)
        }
    }


    fun scheduleAll(context: Context) {
        val startTime = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_SHIFT_START)
        val endTime = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_SHIFT_END)
        if (startTime != null && endTime != null) {
            val start = FormatUtils.timeFormatter.parse(startTime)
            val end = FormatUtils.timeFormatter.parse(endTime)

            if (start != null && end != null) {
                val startHour = start.hours
                val startMinute = start.minutes
                val endHour = end.hours
                val endMinute = end.minutes

                scheduleReminder(context, startHour, startMinute, "Thời khắc quan trọng đã đến", "Đã đến giờ chấm công")
                scheduleReminder(context, endHour, endMinute, "Thời khắc quan trọng đã đến", "Đã đến giờ chấm công")
            }
        }
    }

    fun scheduleReminder(context: Context, hour: Int, minute: Int, title: String, message: String) {

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val delay = calendar.timeInMillis - System.currentTimeMillis()

        val data = Data.Builder()
            .putString("title", title)
            .putString("message", message)
            .putInt("hour", hour)
            .putInt("minute", minute)
            .build()

        val request = OneTimeWorkRequestBuilder<DailyReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "reminder_${hour}_${minute}",
            ExistingWorkPolicy.KEEP, // Không tạo mới nếu đã có
            request
        )
    }
}
