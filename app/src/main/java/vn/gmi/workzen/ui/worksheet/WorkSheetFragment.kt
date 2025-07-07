package vn.gmi.workzen.ui.worksheet

import android.annotation.SuppressLint
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
import androidx.core.util.Consumer
import androidx.core.view.WindowCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.WorkDayAdapter
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.databinding.FragmentWorkSheetBinding
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity
import vn.gmi.workzen.domain.entity.attendance.WorkDayItem
import vn.gmi.workzen.domain.entity.enums.WorkStatus
import vn.gmi.workzen.ui.home.models.EAttendanceType
import vn.gmi.workzen.utils.DateUtils
import java.time.LocalDate
import javax.inject.Inject


@AndroidEntryPoint
class WorkSheetFragment : BaseFragment<FragmentWorkSheetBinding>(), WorkSheetContract.View {

    @Inject
    lateinit var workSheetPresenter: WorkSheetPresenter
    val weekdays = listOf("CN", "T.2", "T.3", "T.4", "T.5", "T.6", "T.7")
    private lateinit var adapter: WorkDayAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWorkSheetBinding {
        return FragmentWorkSheetBinding.inflate(inflater, container, false)
    }

    override fun initBindingData() {
        val headerLayout = binding.weekdayHeader
        val color = ColorUtils.setAlphaComponent(
            ContextCompat.getColor(
                requireContext(),
                R.color.textSecondary
            ), (0.3f * 255).toInt()
        )
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

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_calendar -> {
                    showBottomSelectTimeReport()
                    true
                }

                else -> false
            }
        }

    }

    override fun onSingleClick(v: View?) {
        when (v) {

        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.primary)
        WindowCompat.getInsetsController(
            requireActivity().window,
            requireActivity().window.decorView
        )?.isAppearanceLightStatusBars = false

        workSheetPresenter.attachView(this)

        adapter = WorkDayAdapter(binding.gridWorkSheet)
        binding.gridWorkSheet.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.gridWorkSheet.adapter = adapter


        val now = LocalDate.now()
        workSheetPresenter.getReportAttendanceByMonthYear(now.monthValue, now.year)
        binding.tvTitle.text = "Tháng ${now.monthValue} năm ${now.year}"
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
        Log.d("onGetReportAttendanceByMonthYear", entity.toString());
        val fullData = buildFullMonthDays(entity?.days ?: emptyList(), 6, 2025)
        adapter.setFullData(fullData)
    }

    fun buildFullMonthDays(
        dataFromServer: List<ReportWorkSheetDayEntity>,
        month: Int,
        year: Int
    ): List<WorkDayItem> {
        val result = mutableListOf<WorkDayItem>()

        val daysMap = dataFromServer.associateBy { it.workDate } // map theo chuỗi ngày

        val startOfMonth = LocalDate.of(year, month, 1)
        val endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth())

        // Tính số ngày trống cần thêm phía trước
        val dayOfWeekIndex = (startOfMonth.dayOfWeek.value % 7) // 0 = CN, 1 = T2,...

        // Ngày bắt đầu hiển thị (có thể là cuối tháng trước)
        val firstCalendarDay = startOfMonth.minusDays(dayOfWeekIndex.toLong())

        // Tạo đủ 42 ngày liên tục
        for (i in 0 until 42) {
            val date = firstCalendarDay.plusDays(i.toLong())
            val dateStr = date.toString() // yyyy-MM-dd

            val entity = daysMap[dateStr]
            if (entity != null) {
                result.add(WorkDayItem.WorkDay(entity))
            } else {
                val fake = ReportWorkSheetDayEntity().apply {
                    workDate = dateStr
                    status = null
                    attendanceStatus = null
                    note = null
                    salary = null
                    data = null
                    id = "fakeday-$dateStr"
                }
                result.add(WorkDayItem.WorkDay(fake))
            }
        }

        return result
    }


    private fun showBottomSelectTimeReport() {
        val listener = object : Consumer<Pair<Int, Int>>{
            override fun accept(value: Pair<Int, Int>) {
                val (month, year) = value
                workSheetPresenter.getReportAttendanceByMonthYear(month, year)
                binding.tvTitle.text = "Tháng $month năm $year"
            }
        }
        val bottomSheet = BottomSheetSelectTimeReportFragment()
        bottomSheet.setListener(listener)
        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }

}