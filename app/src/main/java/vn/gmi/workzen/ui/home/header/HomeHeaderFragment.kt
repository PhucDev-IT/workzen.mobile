package vn.gmi.workzen.ui.home.header

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.databinding.FragmentHomeHeaderBinding
import vn.gmi.workzen.ui.home.HomeContract
import vn.gmi.workzen.ui.notification.NotificationActivity
import vn.gmi.workzen.utils.MySharedPreferences
import javax.inject.Inject
@AndroidEntryPoint
class HomeHeaderFragment : BaseFragment<FragmentHomeHeaderBinding>(), HomeHeaderContract.View {

    @Inject lateinit var homeHeaderPresenter: HomeHeaderContract.Presenter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeHeaderBinding {
        return FragmentHomeHeaderBinding.inflate(inflater,container,false)
    }

    override fun initBindingData() {
        val name = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_FULL_NAME)
        binding.tvFullName.text = "Xin chào, $name"

        binding.imgNotification.setOnClickListener(this)
    }

    override fun onSingleClick(v: View?) {
        when(v){
            binding.imgNotification->{
                startActivity(Intent(requireActivity(), NotificationActivity::class.java))
            }
        }
    }

    override fun initView() {
        homeHeaderPresenter.attachView(this)
        homeHeaderPresenter.getTodayInfo()
        homeHeaderPresenter.countNotification()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(message: String) {

    }

    override fun onShowTodayInfo(time: String) {
        binding.tvTime.text = time
    }

    override fun onCountNotification(count: Long) {
        Toast.makeText(requireContext(),count.toString(),Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        homeHeaderPresenter.detachView()
        super.onDestroyView()
    }
}