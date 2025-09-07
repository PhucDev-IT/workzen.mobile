package vn.gmi.workzen.core.extensions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale

fun Context.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}


fun EditText.addCurrencyFormatter() {
    this.addTextChangedListener(object : TextWatcher {
        private var current = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (s.toString() != current) {
                this@addCurrencyFormatter.removeTextChangedListener(this)

                val cleanString = s.toString().replace("[^\\d]".toRegex(), "")
                if (cleanString.isNotEmpty()) {
                    val parsed = cleanString.toLong()
                    val formatter = NumberFormat.getInstance(Locale("vi", "VN"))
                    val formatted = formatter.format(parsed)

                    current = formatted
                    this@addCurrencyFormatter.setText(formatted)
                    this@addCurrencyFormatter.setSelection(formatted.length)
                } else {
                    current = ""
                    this@addCurrencyFormatter.text?.clear()
                }

                this@addCurrencyFormatter.addTextChangedListener(this)
            }
        }
    })
}