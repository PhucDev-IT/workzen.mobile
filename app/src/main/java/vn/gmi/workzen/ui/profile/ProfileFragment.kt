package vn.gmi.workzen.ui.profile

import android.annotation.SuppressLint
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
        presenter.getProfile()
    }

    override fun onSingleClick(v: View?) {
        when(v){
            binding.icLogout ->{

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

    @SuppressLint("SetTextI18n")
    override fun onGetProfileSuccess(profile: ProfileEntity) {
        binding.tvFullName.text = profile.fullName
        binding.tvWorkingTime.text = profile.contracts.last().shift?.name
        val contract = profile.contracts.first { it.isActive }
        binding.tvPosition.text = ContractRole.fromKey(contract.position)
        try{
            binding.tvStartTime.text = "Tham gia từ ${ profile.contracts.last().startDate?.substring(0,4)}"
        }catch (e: Exception){
            Log.e(TAG,e.message?:"Lỗi parser time")
        }
        try{
            Glide.with(this).load(Utils.base64ToBitmap(profile.details?.dg2!!)).into(binding.imgAvatar)
        }catch (e: Exception){
            Log.e(TAG,e.message?:"")
            Glide.with(this).load("https://img.freepik.com/free-photo/emotions-people-concept-headshot-serious-looking-handsome-man-with-beard-looking-confident-determined_1258-26730.jpg?size=626&ext=jpg&uid=R118572234&ga=GA1.1.1965375583.1709184711&semt=ais_user").into(binding.imgAvatar)
        }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}