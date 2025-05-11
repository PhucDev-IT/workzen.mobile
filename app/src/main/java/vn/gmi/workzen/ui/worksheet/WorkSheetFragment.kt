package vn.gmi.workzen.ui.worksheet

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.WindowCompat
import androidx.core.view.setPadding
import vn.gmi.workzen.R
import vn.gmi.workzen.base.BaseContract
import vn.gmi.workzen.base.BaseFragment
import vn.gmi.workzen.databinding.FragmentWorkSheetBinding


class WorkSheetFragment : BaseFragment<FragmentWorkSheetBinding>(),WorkSheetContract.View {

    private lateinit var presenter: WorkSheetPresenter
    val weekdays = listOf("T.2", "T.3", "T.4", "T.5", "T.6", "T.7", "CN")


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWorkSheetBinding {
        return FragmentWorkSheetBinding.inflate(inflater,container,false)
    }

    override fun initBindingData() {
        val headerLayout = binding.weekdayHeader
        val color = ColorUtils.setAlphaComponent(ContextCompat.getColor(requireContext(),R.color.textSecondary), (0.3f * 255).toInt())
        weekdays.forEach {
            val tv = TextView(requireContext()).apply {
                text = it
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.BOLD)
                setPadding(8)
                setBackgroundColor(color)
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
            }
            headerLayout.addView(tv)
        }
    }

    override fun onSingleClick(v: View?) {

    }

    override fun initView() {
        requireActivity().window.statusBarColor  = ContextCompat.getColor(requireContext(),R.color.primary)
        WindowCompat.getInsetsController(requireActivity().window, requireActivity().window.decorView)?.isAppearanceLightStatusBars = false

        presenter = WorkSheetPresenter()
        presenter.attachView(this)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}