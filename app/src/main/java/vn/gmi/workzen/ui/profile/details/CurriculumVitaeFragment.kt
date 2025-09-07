package vn.gmi.workzen.ui.profile.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.FragmentCurriculumVitaeBinding
import vn.gmi.workzen.manager.SessionManager

class CurriculumVitaeFragment : Fragment() {
   private lateinit var _binding: FragmentCurriculumVitaeBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurriculumVitaeBinding.inflate(inflater,container,false)
        initView()
        return binding.root
    }


    private fun initView() {
        val info = SessionManager.profileState.value?.details
        binding.tvValEidNumber.text = info?.eidNumber?:"-"
        binding.tvValName.text = info?.fullName?:"-"
        binding.tvValGender.text = info?.gender?:"-"
        binding.tvValDob.text = info?.dateOfBirth?:"-"
        binding.tvValIssueDate.text = info?.dateOfIssue?:"-"
        binding.tvValDoe.text = info?.dateOfExpiry?:"-"
        binding.tvValPersonalInfo.text = info?.personalIdentification?:"-"
        binding.tvValFatherName.text = info?.fatherName?:"-"
        binding.tvValMotherName.text = info?.motherName?:"-"
        binding.tvValSpouseName.text = info?.spouseName?:"-"
        binding.tvValOldEid.text = info?.oldEidNumber?:"-"
        binding.tvValPlaceOfOrigin.text = info?.placeOfOrigin?:"-"
        binding.tvValPlaceOfResidence.text = info?.placeOfResidence?:"-"
    }
}