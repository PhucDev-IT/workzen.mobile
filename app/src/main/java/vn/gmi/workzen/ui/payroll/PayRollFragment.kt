package vn.gmi.workzen.ui.payroll

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvBalanceMonthAdapter
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.databinding.FragmentPayRollBinding
import vn.gmi.workzen.domain.entity.attendance.StatisticSalaryOfYearEntity
import javax.inject.Inject
import androidx.core.graphics.toColorInt

@AndroidEntryPoint
class PayRollFragment : BaseFragment<FragmentPayRollBinding>(), PayRollContract.View {
    private lateinit var adapter: RvBalanceMonthAdapter
    @Inject
    lateinit var presenter: PayRollPresenter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPayRollBinding {
        return FragmentPayRollBinding.inflate(inflater, container, false)
    }

    override fun initBindingData() {

    }

    override fun onSingleClick(v: View?) {

    }

    override fun initView() {
        presenter.attachView(this)

        adapter = RvBalanceMonthAdapter()
        binding.rvBalanceMonth.adapter = adapter

        presenter.requestGetBalance()

    }

    private fun drawChart(list: List< StatisticSalaryOfYearEntity.StatisticSalaryOfYearDataEntity>) {
        val barChart = binding.barChart

        // 1. Dữ liệu doanh thu 12 tháng
        val entries = mutableListOf<BarEntry>()
        for(i in 0 until list.size){
            entries.add(BarEntry(i.toFloat(), list[i].totalSalary!!.toFloat()))
        }

        val dataSet = BarDataSet(entries, "Doanh thu (triệu VND)")
        dataSet.color = "#4CAF50".toColorInt() // màu xanh lá
        dataSet.valueTextSize = 12f

        // 2. Đặt dữ liệu lên biểu đồ
        val barData = BarData(dataSet)
        barChart.data = barData

        // 3. Tùy chỉnh hiển thị
        val months =
            listOf("T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12")
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(months)
        barChart.xAxis.granularity = 1f
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        barChart.animateY(1000)
        barChart.invalidate()
    }


    override fun showLoading() {
    }

    override fun hideLoading() {

    }

    override fun onResultBalance(response: StatisticSalaryOfYearEntity) {
        var list: MutableList<StatisticSalaryOfYearEntity.StatisticSalaryOfYearDataEntity> =
            mutableListOf()
        response.months?.let { list.addAll(it) }
        for (i in 1 until 13) {
            var item = list.find { it.month == i }
            if (item == null) {
                list?.add(StatisticSalaryOfYearEntity.StatisticSalaryOfYearDataEntity().apply {
                    month = i
                })
            }

        }
        adapter.addAll(list)
        drawChart(list)
    }

    override fun onError(message: String) {

    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}