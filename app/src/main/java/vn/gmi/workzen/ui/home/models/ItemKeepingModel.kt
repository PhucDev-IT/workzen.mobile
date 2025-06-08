package vn.gmi.workzen.ui.home.models

import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.graphics.ColorUtils

class ItemKeepingModel(
    val title: String,
    @DrawableRes val icon: Int,
    val time: String,
    val status: String,
    val reward: String,
    @ColorInt val iconColor: Int,
    val opacity: Float = 0.2f,
    val attendanceType: EAttendanceType
) {
    val backgroundIcon: Int
        get() = ColorUtils.setAlphaComponent(iconColor, (opacity * 255).toInt())
}

enum class EAttendanceType{
    OVERTIME_START, OVERTIME_END, SHIFT_START, SHIFT_END
}
