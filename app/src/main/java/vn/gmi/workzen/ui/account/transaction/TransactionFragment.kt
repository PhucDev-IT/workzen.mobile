package vn.gmi.workzen.ui.account.transaction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.FragmentTransactionBinding
import vn.gmi.workzen.domain.usecase.GetWalletIdByPhoneUseCase
import vn.gmi.workzen.utils.Constants
import javax.inject.Inject

@AndroidEntryPoint
class TransactionFragment : Fragment() {
    private lateinit var _binding: FragmentTransactionBinding
    private val binding get() = _binding

    @Inject lateinit var getWalletIdByPhoneUseCase: GetWalletIdByPhoneUseCase


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        setListener()
        return binding.root
    }

    private fun setListener(){
        binding.btnFindPhone.setOnClickListener {
            findWalletIdByPhone()
        }
    }

    private fun findWalletIdByPhone(){
        val phone = binding.edtPhone.text.toString().trim()
        if(phone.isEmpty() || phone.length < Constants.MIN_LENGTH_PHONE){
            binding.edtPhone.error = "Vui lòng nhập số điện thoại"
            return
        }

        lifecycleScope.launch {
            try{
                val id = getWalletIdByPhoneUseCase.invoke(phone)
                binding.tvResponseFindPhone.text = "Thông tin hợp lệ"
            }catch (e: Exception){
                e.printStackTrace()
                binding.tvResponseFindPhone.text = "Thông tin không hợp lệ"
            }finally {

            }
        }
    }
}