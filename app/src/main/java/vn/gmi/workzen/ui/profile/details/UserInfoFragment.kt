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

        val contract = SessionManager.profile?.contracts?.find { it.isActive }

        binding.tvValuePhone.text = SessionManager.profile?.phone?:"-"
        binding.tvValueEmail.text = SessionManager.profile?.email?:"-"
        binding.tvValueName.text = SessionManager.profile?.fullName?:"-"
        binding.tvValueGender.text = SessionManager.profile?.details?.gender?:"-"
        binding.tvValuePosition.text = contract?.position?:"-"
        binding.tvValueWorkingForm.text = contract?.shift?.name?:"-"
        binding.tvValueDob.text = SessionManager.profile?.details?.dateOfBirth?:"-"

    }

    private fun getData(){

    }

}