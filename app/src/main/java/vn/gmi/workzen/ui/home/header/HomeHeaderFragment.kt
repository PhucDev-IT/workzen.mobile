package vn.gmi.workzen.ui.home.header

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.databinding.FragmentHomeHeaderBinding
import vn.gmi.workzen.ui.home.HomeContract
import vn.gmi.workzen.utils.MySharedPreferences

class HomeHeaderFragment : BaseFragment<FragmentHomeHeaderBinding>(), HomeHeaderContract.View {

    private lateinit var presenter: HomeHeaderContract.Presenter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeHeaderBinding {
        return FragmentHomeHeaderBinding.inflate(inflater,container,false)
    }

    override fun initBindingData() {
        val name = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_FULL_NAME)
        binding.tvFullName.text = "Xin chào, $name"
    }

    override fun onSingleClick(v: View?) {

    }

    override fun initView() {
        presenter = HomeHeaderPresenter()
        presenter.attachView(this)
        presenter.getTodayInfo()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(message: String) {

    }

    override fun onShowTodayInfo(time: String) {

    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}