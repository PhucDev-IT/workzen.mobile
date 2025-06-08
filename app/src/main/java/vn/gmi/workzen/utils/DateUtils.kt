package vn.gmi.workzen.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
object DateUtils {
    const val ISO8601DATEFORMAT: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val YEARMONTHDATFORMAT: String = "yyyy-MM-dd"

    // Cho java.util.Date
    fun formatDate(date: Date?, dateFormat: String?): String? {
        val format = SimpleDateFormat(dateFormat, Locale.getDefault())
        return date?.let { format.format(it) }
    }

    // Cho LocalDate
    fun formatLocalDate(date: LocalDate, dateFormat: String?): String {
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        return date.format(formatter)
    }

    // Cho LocalDateTime
    fun formatLocalDateTime(date: LocalDateTime, dateFormat: String?): String {
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        return date.format(formatter)
    }

    // Parse string về java.util.Date
    fun stringToDate(date: String, dateFormat: String?): Date? {
        return try {
            val format = SimpleDateFormat(dateFormat, Locale.getDefault())
            format.parse(date)
        } catch (ex: Exception) {
            null
        }
    }

    fun stringToLocalDateTime(date: String, dateFormat: String?): LocalDateTime? {
        return try {
            val formatter = DateTimeFormatter.ofPattern(dateFormat)
            LocalDateTime.parse(date, formatter)
        } catch (ex: Exception) {
            null
        }
    }
}
