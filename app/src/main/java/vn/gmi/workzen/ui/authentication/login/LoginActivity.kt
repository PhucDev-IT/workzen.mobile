package vn.gmi.workzen.ui.authentication.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import vn.gmi.workzen.MainActivity
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.databinding.ActivityLoginBinding
import vn.gmi.workzen.data.models.auth.LoginRequestModel
import vn.gmi.workzen.data.models.auth.AuthResponse
import vn.gmi.workzen.utils.Constants

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginContract.View,LoginContract.Presenter>(),LoginContract.View {

    private lateinit var binding:ActivityLoginBinding
    @Inject
    lateinit var loginPresenter: LoginPresenter

    override val layoutView: View
        get() {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            return binding.root
        }

    override fun initPresenter(): LoginContract.Presenter = loginPresenter

    override fun initViews() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun setListener() {
      binding.btnLogin.setOnClickListener(this)
    }

    override fun onSingleClick(v: View?) {
       when(v){
           binding.btnLogin ->{
//               validateData()
               val intent = Intent(this@LoginActivity,MainActivity::class.java)
               startActivity(intent)
           }
       }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onLoginSuccess(model: AuthResponse) {
        Toast.makeText(this@LoginActivity,"OKE",Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
       Toast.makeText(this@LoginActivity,message,Toast.LENGTH_SHORT).show()
    }

    //============================== LOGIC ========================================
    @SuppressLint("HardwareIds")
    private fun validateData(){
        val phone = binding.edtPhone.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if(phone.length < Constants.MIN_LENGTH_PHONE || password.length < Constants.MIN_LENGTH_PASSWORD){
            Toast.makeText(this,"Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show()
            return
        }

        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val manufacturer = Build.MANUFACTURER  // Ví dụ: "Samsung"
        val model = Build.MODEL       // Ví dụ: "Galaxy S22"
        val request = LoginRequestModel(phone,password,model,deviceId,"")
        presenter.requestLogin(request)
    }
}