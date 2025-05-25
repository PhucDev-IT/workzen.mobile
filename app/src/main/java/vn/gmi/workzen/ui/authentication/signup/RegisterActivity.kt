package vn.gmi.workzen.ui.authentication.signup

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.databinding.ActivityLoginBinding
import vn.gmi.workzen.databinding.ActivityRegisterBinding
import vn.gmi.workzen.ui.authentication.signup.fragment.InputOTPFragment
import vn.gmi.workzen.ui.authentication.signup.fragment.NewPasswordFragment
import vn.gmi.workzen.ui.authentication.signup.fragment.PhoneInputFragment

class RegisterActivity : BaseActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View {

    private lateinit var binding: ActivityRegisterBinding

    override val layoutView: View
        get() {
            binding = ActivityRegisterBinding.inflate(layoutInflater)
            return binding.root
        }

    override fun initPresenter(): RegisterContract.Presenter {
        return RegisterPresenter()
    }

    override fun initViews() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showStepView(0)
    }

    private fun showStepView(step: Int) {
        val fragment = when (step) {
            0 -> PhoneInputFragment()
            1 -> InputOTPFragment()
            2 -> NewPasswordFragment()
            else -> throw IllegalArgumentException()
        }
       replaceFragment(R.id.container_register,fragment,"SIGN_UP",null)
    }

    override fun setListener() {

    }

    override fun onSingleClick(v: View?) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun navigateToStep(step: Int) {
        showStepView(step)
    }

    override fun onError(message: String) {

    }

    fun getPresenter() = presenter
}