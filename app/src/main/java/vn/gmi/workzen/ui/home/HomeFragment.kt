package vn.gmi.workzen.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.gmi.workzen.R
import vn.gmi.workzen.base.BaseFragment
import vn.gmi.workzen.databinding.FragmentHomeBinding
import vn.gmi.workzen.ui.home.time_keeping.TimeKeepingFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(),HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    override fun initBindingData() {

    }

    override fun onSingleClick(v: View?) {

    }

    @SuppressLint("CommitTransaction")
    override fun initView() {
        presenter = HomePresenter()
        presenter.attachView(this)

        childFragmentManager.beginTransaction().replace(R.id.timeKeepingFragment, TimeKeepingFragment()).commit()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}