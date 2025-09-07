package vn.gmi.workzen.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import vn.gmi.workzen.R
import vn.gmi.workzen.adapter.RvNewspaperAdapter
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.models.NewspaperModel
import vn.gmi.workzen.databinding.FragmentHomeBinding
import vn.gmi.workzen.domain.entity.user.ProfileEntity
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.ui.authentication.onboard_user.ScanQrCodeActivity
import vn.gmi.workzen.ui.home.header.HomeHeaderFragment
import vn.gmi.workzen.ui.home.time_keeping.TimeKeepingFragment
import vn.gmi.workzen.utils.MySharedPreferences
import javax.inject.Inject


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
        childFragmentManager.beginTransaction().replace(R.id.homeHeaderFragmentContainer, HomeHeaderFragment()).commit()
        adapter = RvNewspaperAdapter()
        binding.rvNewspaper.adapter = adapter
        binding.rvNewspaper.layoutManager = LinearLayoutManager(requireContext())
        presenter = HomePresenter()
        presenter.attachView(this)
        presenter.getNotificationAndEvent()
        onGetProfile()
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

    override fun onResume() {
        super.onResume()
        onGetProfile()
    }

    //================= PRESENTER========================================
    override fun onResultNotificationAndEvents(items: List<NewspaperModel>) {
        adapter.addAll(items)
    }

    private fun onGetProfile() {
        CoroutineScope(Dispatchers.Main).launch {
            SessionManager.profileState.collectLatest { profile ->
                if (profile != null) {
                   if(profile.details?.isVerified == false){
                       binding.viewRequestOnboard.root.visibility = View.VISIBLE
                   }else{
                       binding.viewRequestOnboard.root.visibility = View.GONE
                   }
                }
            }
        }
    }

}