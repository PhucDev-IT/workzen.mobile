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

class NewPasswordFragment : Fragment() {
    private lateinit var _binding:FragmentNewPasswordBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentNewPasswordBinding.inflate(inflater,container,false)
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