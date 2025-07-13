package vn.gmi.workzen.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseFragment
import vn.gmi.workzen.databinding.FragmentProfileBinding
import vn.gmi.workzen.domain.entity.user.IdentificationEntity
import vn.gmi.workzen.domain.entity.contract.ContractRole
import vn.gmi.workzen.domain.entity.user.ProfileEntity
import vn.gmi.workzen.ui.authentication.login.LoginActivity
import vn.gmi.workzen.ui.chat.conversation.ChatActivity
import vn.gmi.workzen.ui.profile.details.ProfileDetailActivity
import vn.gmi.workzen.utils.Utils
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(),ProfileContract.View {
    companion object{
        private val TAG = ProfileFragment::class.java.simpleName
    }

    @Inject lateinit var presenter: ProfileContract.Presenter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater,container,false)
    }

    override fun initBindingData() {
        setListener()
    }


    private fun setListener(){
        binding.llProfileDetail.setOnClickListener(this)
        binding.icChat.setOnClickListener(this)
        binding.icLogout.setOnClickListener(this)
    }

    override fun onSingleClick(v: View?) {
        when(v){
            binding.icChat ->{
                startActivity(Intent(requireContext(), ChatActivity::class.java))
            }
            binding.llProfileDetail ->{
                startActivity(Intent(requireContext(),ProfileDetailActivity::class.java))
            }
            binding.icLogout->{
                logout()
            }
        }
    }

    override fun onError(message: String) {

    }

    override fun initView() {
        Glide.with(this).load(R.drawable.ic_dot_red).into(binding.icDotChat)
        presenter.attachView(this)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    private fun logout(){
        presenter.logout()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finishAffinity()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}