package vn.gmi.workzen.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import vn.gmi.workzen.R
import vn.gmi.workzen.core.base.BaseActivity
import vn.gmi.workzen.databinding.ActivitySplashBinding
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashContract.View, SplashContract.Presenter>(),SplashContract.View {

    @Inject lateinit var splashPresenter: SplashContract.Presenter

    override val layoutView: View
        get() {
            return ActivitySplashBinding.inflate(layoutInflater).root
        }

    override fun initPresenter(): SplashContract.Presenter {
      return splashPresenter
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
        finishAffinity()
    }
}