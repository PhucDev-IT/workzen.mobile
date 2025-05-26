package vn.gmi.workzen.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvNewspaperAdapter
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.models.NewspaperModel
import vn.gmi.workzen.databinding.FragmentHomeBinding
import vn.gmi.workzen.ui.authentication.onboard_user.ScanQrCodeActivity
import vn.gmi.workzen.ui.home.time_keeping.TimeKeepingFragment
import vn.gmi.workzen.utils.MySharedPreferences


class HomeFragment : BaseFragment<FragmentHomeBinding>(),HomeContract.View {

    private lateinit var presenter: HomeContract.Presenter
    private lateinit var adapter:RvNewspaperAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater,container,false)
    }

    override fun initBindingData() {
        binding.viewRequestOnboard.btnStartVerify.setOnClickListener(this)
    }

    override fun onSingleClick(v: View?) {
        when(v){
            binding.viewRequestOnboard.btnStartVerify-> {
                val intent = Intent(requireContext(),ScanQrCodeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("CommitTransaction")
    override fun initView() {
        childFragmentManager.beginTransaction().replace(R.id.timeKeepingFragment, TimeKeepingFragment()).commit()
        adapter = RvNewspaperAdapter()
        binding.rvNewspaper.adapter = adapter
        binding.rvNewspaper.layoutManager = LinearLayoutManager(requireContext())

        presenter = HomePresenter()
        presenter.attachView(this)

        presenter.getNotificationAndEvent()

        val name = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_FULL_NAME)
        binding.tvFullName.text = "Xin chào, $name"
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun onError(message: String) {

    }

    //================= PRESENTER========================================
    override fun onResultNotificationAndEvents(items: List<NewspaperModel>) {
        adapter.addAll(items)
    }
}