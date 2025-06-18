package vn.gmi.workzen.ui.profile.details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.FragmentWorkContractBinding
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.utils.FormatUtils

class WorkContractFragment : Fragment() {

    private lateinit var _binding: FragmentWorkContractBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkContractBinding.inflate(inflater,container,false)
        initView()
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    private fun initView() {
        val contract = SessionManager.profile?.contracts?.find { it.isActive }

        binding.tvJobName.text = contract?.jobName?:"-"
        binding.shiftName.text = contract?.shift?.name?:"-"
        binding.shiftTime.text = ("${contract?.shift?.startTime} - ${contract?.shift?.endTime}")
        binding.tvContractTime.text = "${contract?.startDate} - ${contract?.expiryDate}"
        binding.tvPosition.text = contract?.position?:"-"
        binding.tvSalaryBase.text = FormatUtils.numberFormat.format(contract?.baseSalary) ?:"-"
        binding.tvAddressWork.text = contract?.company?.address?:"-"
        binding.tvCompanyName.text = contract?.company?.name?:"-"


    }
}