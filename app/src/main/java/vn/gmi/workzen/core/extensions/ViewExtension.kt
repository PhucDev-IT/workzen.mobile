package vn.gmi.workzen.core.extensions

import android.content.Context

fun Context.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}
