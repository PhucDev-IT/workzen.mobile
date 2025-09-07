package vn.gmi.workzen.core.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import vn.gmi.workzen.R


class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val textView: TextView
    private val progressBar: ProgressBar

    init {
        LayoutInflater.from(context).inflate(R.layout.button_loading, this, true)
        textView = findViewById(R.id.btnText)
        progressBar = findViewById(R.id.btnProgress)

        // Đọc attribute từ XML
        context.theme.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0).apply {
            try {
                // Text
                textView.text = getString(R.styleable.LoadingButton_text) ?: ""
                textView.textSize = getDimension(R.styleable.LoadingButton_textSize, 16f)
                textView.setTextColor(getColor(R.styleable.LoadingButton_textColor, Color.BLACK))

                // Font family
                getString(R.styleable.LoadingButton_fontFamily)?.let { fontName ->
                    try {
                        val typeface = Typeface.create(fontName, Typeface.NORMAL)
                        textView.typeface = typeface
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                // Text style (bold, italic...)
                val styleResId = getResourceId(R.styleable.LoadingButton_style, 0)
                if (styleResId != 0) {
                    TextViewCompat.setTextAppearance(textView, styleResId)
                }

            } finally {
                recycle()
            }
        }

    }

    fun setText(text: String) {
        textView.text = text
    }

    fun showLoading() {
        textView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
        isEnabled = false
    }

    fun hideLoading() {
        textView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        isEnabled = true
    }
}

