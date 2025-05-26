package vn.gmi.workzen.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<SplashContract.View, SplashContract.Presenter>(),SplashContract.View {

    override val layoutView: View
        get() {
            return ActivitySplashBinding.inflate(layoutInflater).root
        }

    override fun initPresenter(): SplashContract.Presenter {
      return SplashPresenter()
    }

    override fun initViews() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        presenter.checkApp()
    }

    override fun setListener() {

    }

    override fun onSingleClick(v: View?) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(message: String) {

    }

    override fun goToView(clz: Class<*>) {
        startActivity(Intent(this,clz))
    }
}