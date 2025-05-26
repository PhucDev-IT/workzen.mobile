package vn.gmi.workzen.ui.splash

import android.os.Handler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import vn.gmi.workzen.MainActivity
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.ui.authentication.login.LoginActivity
import vn.gmi.workzen.utils.MySharedPreferences

class SplashPresenter : BasePresenter<SplashContract.View>(), SplashContract.Presenter {

    private val presenterJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + presenterJob)

    override fun checkApp() {
        Handler().postDelayed({
            if(checkAccessToken()){
                getView()?.goToView(MainActivity::class.java)
            }else{
                getView()?.goToView(LoginActivity::class.java)
            }
        },2000)
    }

    private fun checkAccessToken():Boolean{
        val token = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_BEAR_ACCESS_TOKEN)
        return !token.isNullOrEmpty()
    }

    override fun detachView() {
        presenterJob.cancel()
        super.detachView()
    }
}