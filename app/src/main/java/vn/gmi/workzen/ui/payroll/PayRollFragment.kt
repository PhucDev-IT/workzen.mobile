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
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvBalanceMonthAdapter
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.data.models.PayRollOfYearModel
import vn.gmi.workzen.databinding.FragmentPayRollBinding


class PayRollFragment : BaseFragment<FragmentPayRollBinding>(), PayRollContract.View {
    private lateinit var adapter: RvBalanceMonthAdapter
    private lateinit var presenter: PayRollPresenter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPayRollBinding {
        return FragmentPayRollBinding.inflate(inflater,container,false)
    }

    override fun initBindingData() {
        chart()
    }

    override fun onSingleClick(v: View?) {

    }

    override fun initView() {
        presenter = PayRollPresenter()
        presenter.attachView(this)

        adapter = RvBalanceMonthAdapter()
        binding.rvBalanceMonth.adapter = adapter

        presenter.requestGetBalance()

    }

    private fun chart(){
        val barChart = binding.barChart

// 1. Dữ liệu doanh thu 12 tháng
        val entries = listOf(
            BarEntry(0f, 10f),  // Tháng 1: 10 triệu
            BarEntry(1f, 15f),
            BarEntry(2f, 12f),
            BarEntry(3f, 18f),
            BarEntry(4f, 20f),
            BarEntry(5f, 25f),
            BarEntry(6f, 22f),
            BarEntry(7f, 28f),
            BarEntry(8f, 30f),
            BarEntry(9f, 27f),
            BarEntry(10f, 35f),
            BarEntry(11f, 40f)  // Tháng 12: 40 triệu
        )

        val dataSet = BarDataSet(entries, "Doanh thu (triệu VND)")
        dataSet.color = Color.parseColor("#4CAF50") // màu xanh lá
        dataSet.valueTextSize = 12f

// 2. Đặt dữ liệu lên biểu đồ
        val barData = BarData(dataSet)
        barChart.data = barData

// 3. Tùy chỉnh hiển thị
        val months = listOf("T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12")
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

    override fun onResultBalance(items: List<PayRollOfYearModel>) {
        adapter.addAll(items)
    }

    override fun onError(message: String) {

    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}