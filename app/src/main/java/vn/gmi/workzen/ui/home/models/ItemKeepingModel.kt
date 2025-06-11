package vn.gmi.workzen.ui.home.models

import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.graphics.ColorUtils
import java.time.LocalDateTime
import java.time.LocalTime

class ItemKeepingModel(
    val shiftId:String,
    val title: String,
    @DrawableRes val icon: Int,
    val time: LocalDateTime?,
    @ColorInt val iconColor: Int,
    val opacity: Float = 0.2f,
    val allowAttendance: Boolean = false,
    val targetTime: LocalTime? = null,
    val attendanceType: EAttendanceType = EAttendanceType.SHIFT_START
) {
    val backgroundIcon: Int
        get() = ColorUtils.setAlphaComponent(iconColor, (opacity * 255).toInt())
}

enum class EAttendanceType{
    OVERTIME_START, OVERTIME_END, SHIFT_START, SHIFT_END,
    SHIFT_LATE, BONUS,ABSENT,DAY_OFF,SHIFT_ON_TIME,SHIFT_HALF
}
