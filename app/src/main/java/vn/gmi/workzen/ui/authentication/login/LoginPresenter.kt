package vn.gmi.workzen.ui.authentication.login

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.core.extensions.standardizationNumberPhone
import vn.gmi.workzen.networks.models.request.LoginRequestModel
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.models.response.auth.LoginResponseModel
import vn.gmi.workzen.networks.rest.ApiResult
import vn.gmi.workzen.networks.rest.networkCallback
import vn.gmi.workzen.networks.rest.onError
import vn.gmi.workzen.networks.rest.onSuccess
import vn.gmi.workzen.utils.MySharedPreferences

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun requestLogin(model: LoginRequestModel) {
        scope.launch {
            try {
                getView()?.showLoading()
                model.numberPhone = standardizationNumberPhone(model.numberPhone)

                networkCallback {
                    ApiService.instance.authenticationService.requestLogin(model)
                }.onSuccess {
                    storeData(it)
                    getView()?.onLoginSuccess(it)
                }.onError {
                    getView()?.onError(it)
                }

            } catch (e: Exception) {
                getView()?.onError(e.message ?: "Unknown error")
            } finally {
                getView()?.hideLoading()
            }
        }

    }


    private fun storeData(model:LoginResponseModel){
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_USER_ID,model!!.id)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_FULL_NAME,model.fullName)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_PHONE,model.phone)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_EMAIL,model.email?:"")
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_AVATAR,model.avatar?:"")
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_ROLES,Gson().toJson(model.roles))
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_BEAR_ACCESS_TOKEN,model.bearToken)

        MySharedPreferences.setBooleanValue(SharedPreferenceKey.KEY_IS_LOGIN, true)
    }


    override fun detachView() {
        super.detachView()
    }

}