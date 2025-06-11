package vn.gmi.workzen.ui.worksheet

import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.WindowCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.WorkDayAdapter
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.databinding.FragmentWorkSheetBinding
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity
import javax.inject.Inject


@AndroidEntryPoint
class WorkSheetFragment : BaseFragment<FragmentWorkSheetBinding>(),WorkSheetContract.View {

    @Inject lateinit var workSheetPresenter: WorkSheetPresenter
    val weekdays = listOf("T.2", "T.3", "T.4", "T.5", "T.6", "T.7", "CN")
    private lateinit var adapter: WorkDayAdapter

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

        workSheetPresenter.attachView(this)

        adapter = WorkDayAdapter()
        binding.gridWorkSheet.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.gridWorkSheet.adapter  = adapter
        workSheetPresenter.getReportAttendanceByMonthYear(6,2025)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(message: String) {

    }

    override fun onDestroyView() {
        workSheetPresenter.detachView()
        super.onDestroyView()
    }


    override fun onGetReportAttendanceByMonthYear(entity: ReportWorkSheetMonthYearEntity?) {
        Log.d("onGetReportAttendanceByMonthYear",entity.toString());
        adapter.addAll(entity?.days?: emptyList())
    }
}