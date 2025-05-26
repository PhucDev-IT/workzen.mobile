package vn.gmi.workzen.core.constants

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import vn.gmi.workzen.MyApplication
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.CustomAppToastBinding
import kotlin.math.tan

object AppToast {

    fun showSuccess(context: Context, message: String) {
        val binding = CustomAppToastBinding.inflate(LayoutInflater.from(context))
        binding.tvMessage.text = message
        binding.root.setBackgroundResource(R.color.success)

        Toast(context).apply {
            duration = Toast.LENGTH_SHORT
            view = binding.root
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
            show()
        }
    }

    fun showError(context: Context, message: String) {
        val binding = CustomAppToastBinding.inflate(LayoutInflater.from(context))
        binding.tvMessage.text = message
        binding.root.setBackgroundResource(R.color.failed)

        Toast(context).apply {
            duration = Toast.LENGTH_LONG
            view = binding.root
            setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 100)
            show()
        }
    }
}
