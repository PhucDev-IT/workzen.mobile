package vn.gmi.workzen.ui.authentication.signup.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import vn.gmi.workzen.MyApplication
import vn.gmi.workzen.R
import vn.gmi.workzen.core.constants.AppToast
import vn.gmi.workzen.databinding.FragmentInputOTPBinding
import vn.gmi.workzen.services.PushNotification
import vn.gmi.workzen.ui.authentication.signup.RegisterActivity
import vn.gmi.workzen.ui.authentication.signup.RegisterContract
import vn.gmi.workzen.utils.Utils


class InputOTPFragment : Fragment() {
    private lateinit var _binding: FragmentInputOTPBinding
    private val binding get() = _binding
    private lateinit var presenter: RegisterContract.Presenter
    private var otpCode = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputOTPBinding.inflate(inflater,container,false)
        presenter = (requireActivity() as RegisterActivity).getPresenter()
        sendOTP()
        setListener()
        return binding.root
    }

    private fun setListener(){
        binding.header.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        onOTPInputComplete(binding.edt1, binding.edt2, null)
        onOTPInputComplete(binding.edt2, binding.edt3, binding.edt1)
        onOTPInputComplete(binding.edt3, binding.edt4, binding.edt2)
        onOTPInputComplete(binding.edt4, binding.edt5, binding.edt3)
        onOTPInputComplete(binding.edt5, binding.edt6, binding.edt4)
        onOTPInputComplete(binding.edt6, null, binding.edt5){
            if (isOTPValid()) {
                val otp = getOtpCode()
                if(otp != otpCode){
                    AppToast.showError(requireContext(),"Mã xác nhận không chính xác")
                    return@onOTPInputComplete
                }
                (requireActivity() as RegisterActivity).navigateToStep(2)
                clear()
            }
        }

    }

    private fun onOTPInputComplete(current: EditText, next: View? = null, prev: View? = null, onComplete: (() -> Unit)? = null) {
        current.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
                    next?.requestFocus() ?: onComplete?.invoke()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        current.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                if (current.text.isEmpty()) {
                    prev?.requestFocus()
                    return@setOnKeyListener true
                }
            }
            false
        }
    }
    private fun isOTPValid(): Boolean {
        return listOf(binding.edt1, binding.edt2, binding.edt3, binding.edt4, binding.edt5, binding.edt6).all {
            it.text.length == 1
        }
    }

    private fun getOtpCode(): String {
        return binding.edt1.text.toString() +
                binding.edt2.text.toString() +
                binding.edt3.text.toString() +
                binding.edt4.text.toString() +
                binding.edt5.text.toString() +
                binding.edt6.text.toString()
    }

    override fun onResume() {
       clear()
        super.onResume()
    }

    private fun clear(){
        binding.edt1.text.clear()
        binding.edt2.text.clear()
        binding.edt3.text.clear()
        binding.edt4.text.clear()
        binding.edt5.text.clear()
        binding.edt6.text.clear()
    }

    private fun sendOTP(){
        otpCode = Utils.generateOTP()
        PushNotification.sendSimpleNotification("OTP Code",otpCode,MyApplication.CHANNEL_NORMAL)
    }

}