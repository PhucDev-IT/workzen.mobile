package vn.gmi.workzen.ui.authentication.login

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.networks.models.request.LoginRequestModel
import vn.gmi.workzen.networks.models.response.auth.AuthenticationResponse
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.utils.MySharedPreferences

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {
    private val presenterJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + presenterJob)

    override fun requestLogin(model: LoginRequestModel) {
        scope.launch {
            try {
                getView()?.showLoading()
                model.numberPhone = standardizationNumberPhone(model.numberPhone)
                val auth = withContext(Dispatchers.IO) {
                    ApiService.instance.authenticationService.requestLogin(model)
                }
                storeData(auth)
                getView()?.onLoginSuccess(auth)
            } catch (e: Exception) {
                getView()?.onError(e.message.toString())
            } finally {
                getView()?.hideLoading()
            }
        }
    }
    private fun standardizationNumberPhone(phone: String): String {
        return if (phone.startsWith("0")) {
            phone.replaceFirst("0", "+84")
        } else {
            phone
        }
    }

    private fun storeData(model:AuthenticationResponse){
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_USER_ID,model.user!!.id)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_FULL_NAME,model.user.fullName)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_PHONE,model.user.phone)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_EMAIL,model.user.email?:"")
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_AVATAR,model.user.avatar?:"")
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_ROLES,Gson().toJson(model.user.roles))
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_BEAR_ACCESS_TOKEN,model.bearToken)

        MySharedPreferences.setBooleanValue(SharedPreferenceKey.KEY_IS_LOGIN, true)
    }


    override fun detachView() {
        presenterJob.cancel()
        super.detachView()
    }

}