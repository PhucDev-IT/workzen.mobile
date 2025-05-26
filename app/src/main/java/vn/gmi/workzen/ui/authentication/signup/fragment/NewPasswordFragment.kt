package vn.gmi.workzen.ui.authentication.signup.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import vn.gmi.workzen.R
import vn.gmi.workzen.core.extensions.enablePasswordToggle
import vn.gmi.workzen.databinding.FragmentNewPasswordBinding
import vn.gmi.workzen.ui.authentication.signup.RegisterActivity
import vn.gmi.workzen.ui.authentication.signup.RegisterContract

class NewPasswordFragment : Fragment() {
    private lateinit var _binding:FragmentNewPasswordBinding
    private val binding get() = _binding
    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentNewPasswordBinding.inflate(inflater,container,false)
        presenter = (requireActivity() as RegisterActivity).getPresenter()
        initView()
        setListeners()
        return binding.root
    }

    private fun initView(){
        binding.header.tvHeader.text = "Tạo mật khẩu"
    }

    private fun setListeners(){
        binding.header.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        setupPasswordToggleShared(binding.edtPassword, binding.edtRePassword)

        binding.btnContinue.setOnClickListener{
            register()
        }

    }

    private fun register(){
        val password = binding.edtPassword.text.toString().trim()
        val rePassword = binding.edtRePassword.text.toString().trim()

        if(password.isEmpty()){
            binding.edtPassword.error = "Mật khẩu không được để trống"
            return
        }
        if(rePassword.isEmpty()){
            binding.edtRePassword.error = "Mật khẩu không được để trống"
            return
        }

        if(password != rePassword){
            binding.edtRePassword.error = "Mật khẩu không khớp"
            return
        }
        presenter.register(password)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupPasswordToggleShared(
        passwordField: AppCompatEditText,
        rePasswordField: AppCompatEditText,
        visibleIcon: Int = R.drawable.ic_visibility,
        hiddenIcon: Int = R.drawable.ic_visibility_off
    ) {
        var isVisible = false

        passwordField.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = passwordField.compoundDrawables[2] ?: return@setOnTouchListener false
                val touchStart = passwordField.width - passwordField.paddingEnd - drawableEnd.intrinsicWidth
                if (event.x >= touchStart) {
                    isVisible = !isVisible
                    val inputType = if (isVisible) {
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    } else {
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    }

                    passwordField.inputType = inputType
                    rePasswordField.inputType = inputType

                    passwordField.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        ContextCompat.getDrawable(v.context, if (isVisible) hiddenIcon else visibleIcon), null)

                    // giữ lại con trỏ
                    passwordField.setSelection(passwordField.text?.length ?: 0)
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

}