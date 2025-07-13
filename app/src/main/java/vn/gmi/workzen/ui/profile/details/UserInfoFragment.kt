package vn.gmi.workzen.ui.profile.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.FragmentUserInfoBinding
import vn.gmi.workzen.manager.SessionManager


class UserInfoFragment : Fragment() {

    private lateinit var _binding: FragmentUserInfoBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserInfoBinding.inflate(inflater,container,false)
        initView()
        return binding.root
    }


    private fun initView(){

        val contract = SessionManager.profileState.value?.contracts?.find { it.isActive }

        binding.tvValuePhone.text = SessionManager.profileState.value?.phone?:"-"
        binding.tvValueEmail.text = SessionManager.profileState.value?.email?:"-"
        binding.tvValueName.text = SessionManager.profileState.value?.fullName?:"-"
        binding.tvValueGender.text = SessionManager.profileState.value?.details?.gender?:"-"
        binding.tvValuePosition.text = contract?.position?:"-"
        binding.tvValueWorkingForm.text = contract?.shift?.name?:"-"
        binding.tvValueDob.text = SessionManager.profileState.value?.details?.dateOfBirth?:"-"

    }

    private fun getData(){

    }

}