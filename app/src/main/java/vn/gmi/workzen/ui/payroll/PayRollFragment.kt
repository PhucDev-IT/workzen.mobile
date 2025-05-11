package vn.gmi.workzen.ui.payroll

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvBalanceMonthAdapter
import vn.gmi.workzen.base.BaseFragment
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

    override fun showLoading() {
    }

    override fun hideLoading() {

    }

    override fun onResultBalance(items: List<PayRollOfYearModel>) {
        adapter.addAll(items)
    }


    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}