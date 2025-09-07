package vn.gmi.workzen.ui.account.transaction

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import vn.gmi.workzen.R
import vn.gmi.workzen.core.constants.AppToast
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.core.extensions.addCurrencyFormatter
import vn.gmi.workzen.data.models.request.wallet.CreateTransactionReq
import vn.gmi.workzen.databinding.FragmentTransactionBinding
import vn.gmi.workzen.domain.entity.enums.TransactionType
import vn.gmi.workzen.domain.entity.enums.WalletType
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity
import vn.gmi.workzen.domain.usecase.CreateTransactionUseCase
import vn.gmi.workzen.domain.usecase.GetLinkedWalletLocalUseCase
import vn.gmi.workzen.domain.usecase.GetLinkedWalletsUseCase
import vn.gmi.workzen.domain.usecase.GetWalletIdByPhoneUseCase
import vn.gmi.workzen.ui.account.BottomSheetAccountPaymentFragment
import vn.gmi.workzen.ui.account.WalletListener
import vn.gmi.workzen.utils.Constants
import vn.gmi.workzen.utils.MySharedPreferences
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

@AndroidEntryPoint
class TransactionFragment : Fragment() {
    private lateinit var _binding: FragmentTransactionBinding
    private val binding get() = _binding
    private var receiverId : String?=null

    private var myWallet : LinkedWalletEntity?=null
    private var callback: WalletListener? = null

    @Inject lateinit var getWalletIdByPhoneUseCase: GetWalletIdByPhoneUseCase
    @Inject lateinit var createTransactionUseCase: CreateTransactionUseCase
    @Inject lateinit var getLinkedWalletsUseCase: GetLinkedWalletsUseCase
    @Inject lateinit var getLinkedWalletLocalUseCase: GetLinkedWalletLocalUseCase


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
        binding.btnTransfer.setOnClickListener {
            createTransaction()
        }

        binding.edtPhone.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence?, // nội dung hiện tại của EditText trước khi thay đổi
                start: Int,       // vị trí bắt đầu thay đổi
                count: Int,       // số ký tự sẽ bị thay thế/xoá
                after: Int        // số ký tự sẽ được thêm mới
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?, // nội dung mới (đang trong quá trình thay đổi)
                start: Int,       // vị trí thay đổi
                before: Int,      // số ký tự vừa bị xoá
                count: Int        // số ký tự vừa được thêm
            ) {

            }

            override fun afterTextChanged(s: Editable?) {
                receiverId = null
                binding.tvResponseFindPhone.text = null
            }
        })


        binding.edtAmount.addCurrencyFormatter()


    }


    private fun getLinkedWallets(){
        lifecycleScope.launch {
            try {
                val userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)

                val local = getLinkedWalletLocalUseCase.invoke(userId ?: "")
                if(local.isNotEmpty()){
                    myWallet = local.find { it.walletInfo?.type == WalletType.SYSTEM_WALLET.name }
                }
                val result = getLinkedWalletsUseCase.invoke(userId ?: "")
                callback?.onReloadWalletSystem(result)
               myWallet = result.find { it.walletInfo?.type == WalletType.SYSTEM_WALLET.name }
            }catch (e: Exception){
                e.printStackTrace()
            }
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
               val  id = getWalletIdByPhoneUseCase.invoke(phone)
                if(id == myWallet?.idLinkedWallet){
                    binding.edtPhone.error = "Thông tin không hợp lệ"
                    return@launch
                }
                receiverId = id
                handleFindPhone(true)
            }catch (e: Exception){
                e.printStackTrace()
                handleFindPhone(false)
            }finally {

            }
        }
    }

    private fun handleFindPhone(isSuccess: Boolean){
        if(isSuccess){
            val color = ContextCompat.getColor(requireContext(), R.color.green)
            binding.tvResponseFindPhone.setTextColor(color)
            binding.tvResponseFindPhone.text = "Thông tin hợp lệ"
        }else{
            val color = ContextCompat.getColor(requireContext(), R.color.failed)
            binding.tvResponseFindPhone.setTextColor(color)
            binding.tvResponseFindPhone.text = "Thông tin không hợp lệ"
        }

        if(isSuccess && binding.edtAmount.text.toString().trim().isNotEmpty()){
            binding.btnTransfer.isEnabled = true
           val color = ContextCompat.getColor(requireContext(), R.color.primary)
            binding.btnTransfer.backgroundTintList = ColorStateList.valueOf(color)
        }else{
            binding.btnTransfer.isEnabled = false
            val color = ContextCompat.getColor(requireContext(), R.color.gray_light)
            binding.btnTransfer.backgroundTintList = ColorStateList.valueOf(color)
        }
    }


    private fun createTransaction(){
        val amount = binding.edtAmount.text.toString().trim()
        if(amount.isEmpty()){
            binding.edtAmount.error = "Vui lòng nhập số tiền"
            return
        }

        if(BigDecimal(amount) > BigDecimal(myWallet?.balance ?: "0")){
            AppToast.showError(requireContext(),"Số dư không đủ")
            return
        }

        val userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)
        val req = CreateTransactionReq().apply {
            senderWalletLinkedId = myWallet?.idLinkedWallet
            receiverWalletLinkedId = receiverId
            type = TransactionType.TRANSFER_USER
            this.amount = amount
            this.transactionTime = LocalDateTime.now().toString()
            this.recipientType = "user"
        }

        lifecycleScope.launch {
            try {
                binding.btnTransfer.showLoading()
                val maps = mapOf(
                    "userId" to userId!!,
                    "request" to req
                )
                val result = createTransactionUseCase.invoke(maps)
                AppToast.showSuccess(requireContext(),"Giao dịch thành công")
                getLinkedWallets()
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                binding.btnTransfer.hideLoading()
            }
        }
    }

    override fun onResume() {
        getLinkedWallets()
        super.onResume()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = parentFragment as? WalletListener
    }
    override fun onDetach() {
        super.onDetach()
        callback = null
    }
}