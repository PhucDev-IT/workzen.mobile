package vn.gmi.workzen.ui.authentication.signup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.gmi.workzen.R
import vn.gmi.workzen.databinding.FragmentPhoneInputBinding
import vn.gmi.workzen.ui.authentication.signup.RegisterActivity
import vn.gmi.workzen.ui.authentication.signup.RegisterContract
import vn.gmi.workzen.ui.authentication.signup.RegisterPresenter


class PhoneInputFragment : Fragment() {
    private lateinit var _binding: FragmentPhoneInputBinding
    private val binding get() = _binding
    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhoneInputBinding.inflate(inflater, container, false)
        presenter = (requireActivity() as RegisterActivity).getPresenter()

        setListener()
        return binding.root
    }

    private fun setListener() {
        binding.btnContinue.setOnClickListener {
            if (binding.edtPhone.text.toString().isEmpty()) {
                binding.tlPhone.error = "Vui lòng nhập số điện thoại"
            } else {
                presenter.requestVerifyPhone(binding.edtPhone.text.toString())
            }
        }

        binding.header.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}