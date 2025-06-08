package vn.gmi.workzen.ui.splash

import android.os.Handler
import android.util.Base64
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.json.JSONObject
import vn.gmi.workzen.MainActivity
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.domain.usecase.VerifyTokenUseCase
import vn.gmi.workzen.ui.authentication.login.LoginActivity
import vn.gmi.workzen.utils.MySharedPreferences
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val verifyTokenUseCase: VerifyTokenUseCase
): BasePresenter<SplashContract.View>(), SplashContract.Presenter {

    override fun checkApp() {
        val token = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_BEAR_ACCESS_TOKEN)
        if(token == null || isTokenExpired(token)){
            getView()?.goToView(LoginActivity::class.java)
            return
        }
        scope.launch {
            try{
                val result = verifyTokenUseCase.invoke(token)
                if (result){
                    getView()?.goToView(MainActivity::class.java)
                    return@launch
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
            getView()?.goToView(LoginActivity::class.java)
        }
    }


    fun isTokenExpired(token: String): Boolean {
        try {
            val parts = token.split(".")
            if (parts.size != 3) return true

            val payloadEncoded = parts[1]
            val decodedBytes = Base64.decode(payloadEncoded, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            val payloadJson = JSONObject(String(decodedBytes))

            val exp = payloadJson.getLong("exp") // exp là Unix timestamp (giây)
            val currentTime = System.currentTimeMillis() / 1000 // hiện tại (giây)

            return currentTime >= exp
        } catch (e: Exception) {
            e.printStackTrace()
            return true // nếu lỗi khi decode => coi như hết hạn
        }
    }

}