package vn.gmi.workzen.ui.authentication.login

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import vn.gmi.workzen.MainActivity
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.core.constants.AppToast
import vn.gmi.workzen.core.constants.DialogLoading
import vn.gmi.workzen.databinding.ActivityLoginBinding
import vn.gmi.workzen.networks.models.request.LoginRequestModel
import vn.gmi.workzen.networks.models.response.auth.LoginResponseModel
import vn.gmi.workzen.ui.authentication.signup.RegisterActivity
import vn.gmi.workzen.utils.Constants

class LoginActivity : BaseActivity<LoginContract.View,LoginContract.Presenter>(),LoginContract.View {

    private lateinit var binding:ActivityLoginBinding


    override val layoutView: View
        get() {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            return binding.root
        }

    override fun initPresenter(): LoginContract.Presenter {
        return LoginPresenter()
    }


    override fun initViews() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        requestPermission(arrayListOf(Manifest.permission.POST_NOTIFICATIONS)){}

    }

    override fun setListener() {
        binding.btnLogin.setOnClickListener(this)
        binding.tvSignUp.setOnClickListener(this)
    }

    override fun onSingleClick(v: View?) {
        when(v){
            binding.btnLogin ->{
              validateData()
            }
            binding.tvSignUp->{
                startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
            }
        }
    }



    override fun onLoginSuccess(model: LoginResponseModel) {
        AppToast.showSuccess(this,"Đăng nhập thành công")
        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
        finish()
    }

    override fun onError(message: String) {
        AppToast.showError(this,message)
    }
    //============================== LOGIC ========================================
    @SuppressLint("HardwareIds")
    private fun validateData(){
        val phone = binding.edtPhone.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if(phone.length < Constants.MIN_LENGTH_PHONE || password.length < Constants.MIN_LENGTH_PASSWORD){
            AppToast.showError(this,"Tài khoản hoặc mật khẩu không hợp lệ")
            return
        }

        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val manufacturer = Build.MANUFACTURER  // Ví dụ: "Samsung"
        val model = Build.MODEL       // Ví dụ: "Galaxy S22"
        val request = LoginRequestModel(phone,password,model,deviceId,"")
        presenter.requestLogin(request)
    }
}