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
import vn.gmi.workzen.adapter.WorkSheetCalendarAdapter
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
import kotlin.ranges.until


@AndroidEntryPoint
class WorkSheetFragment : BaseFragment<FragmentWorkSheetBinding>(), WorkSheetContract.View {

    @Inject
    lateinit var workSheetPresenter: WorkSheetPresenter
    val weekdays = listOf("T.2", "T.3", "T.4", "T.5", "T.6", "T.7", "CN")
    private lateinit var adapter: WorkSheetCalendarAdapter

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

        adapter = WorkSheetCalendarAdapter(object : Consumer<ReportWorkSheetDayEntity>{
            override fun accept(value: ReportWorkSheetDayEntity) {
                showBottomSheetInfo(value)
            }
        })
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
        val matrix = buildMatrix(entity?.days ?: emptyList())
        val flatten = matrix?.flatten()

        flatten?.let {
            adapter.setFullData(it)
        }
    }


    private fun buildMatrix(dataFromServer: List<ReportWorkSheetDayEntity>): Array<Array<ReportWorkSheetDayEntity>>? {
        val columns = 7 // weekdays.size
        val rows = 6
        val matrix = Array(rows) { Array(columns) { ReportWorkSheetDayEntity() } }

        // Step 1: Get first date
        val firstDate = dataFromServer.firstOrNull()?.workDate ?: return null

        // Step 2: Get day of week
        val date = DateUtils.stringToLocalDate(firstDate)
        val dayOfWeek = date?.dayOfWeek?.value ?: return null // 1=Monday, 7=Sunday

        val firstIndex = dayOfWeek - 1 // 0-based index
        var currentColumn = firstIndex
        var currentRow = 0

        for (i in dataFromServer.indices) {
            matrix[currentRow][currentColumn] = dataFromServer[i]

            currentColumn++
            if (currentColumn == columns) {
                currentColumn = 0
                currentRow++
            }
        }

        for ((i, row) in matrix.withIndex()) {
            for ((j, person) in row.withIndex()) {
                println("[$i][$j] - dayOfWeek = $dayOfWeek, person = $person")
            }
        }

        return matrix
    }


    private fun showBottomSheetInfo(entity:ReportWorkSheetDayEntity){
        val bottomSheet = BottomSheetInfoAttendance.newInstance(entity.id)
        bottomSheet.show(childFragmentManager, bottomSheet.tag)

    }

    private fun showBottomSelectTimeReport() {
        val listener = object : Consumer<Pair<Int, Int>> {
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