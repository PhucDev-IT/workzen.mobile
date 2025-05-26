package vn.gmi.workzen.core.constants

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.CustomLoadingAnimationBinding

object DialogLoading {
    private var dialogLoading: Dialog? = null

    fun showLoading(context: Context) {
        if (dialogLoading?.isShowing == true) return

        dialogLoading = Dialog(context)
        val binding = CustomLoadingAnimationBinding.inflate(LayoutInflater.from(context))
        dialogLoading!!.setContentView(binding.root)

        dialogLoading!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogLoading!!.setCancelable(false)

        binding.lvAnimation.playAnimation()

        dialogLoading!!.show()

    }

    fun hideLoading() {
        dialogLoading?.dismiss()
        dialogLoading = null
    }
}
