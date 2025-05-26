package vn.gmi.workzen.core.extensions

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.InputType
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import vn.gmi.workzen.R

@SuppressLint("ClickableViewAccessibility")
fun AppCompatEditText.enablePasswordToggle(
    iconVisible: Int = R.drawable.ic_visibility,
    iconHidden: Int = R.drawable.ic_visibility_off
) {
    setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            val drawableEnd = compoundDrawables[2] ?: return@setOnTouchListener false
            val touchAreaStart = width - paddingEnd - drawableEnd.intrinsicWidth
            if (event.x >= touchAreaStart) {
                val isPasswordVisible = inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                val cursorPosition = selectionStart

                inputType = if (isPasswordVisible) {
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                } else {
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                }

                val iconRes = if (isPasswordVisible) iconVisible else iconHidden
                setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, iconRes), null)
                setSelection(cursorPosition)
                return@setOnTouchListener true
            }
        }
        false
    }
}
