package vn.gmi.workzen.ui.authentication.login

import com.google.gson.Gson
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.data.models.auth.LoginRequestModel
import vn.gmi.workzen.data.models.auth.AuthResponse
import vn.gmi.workzen.domain.usecase.LoginUseCase
import vn.gmi.workzen.utils.MySharedPreferences

class LoginPresenter @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BasePresenter<LoginContract.View>(), LoginContract.Presenter {
    private val presenterJob = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + presenterJob)

    override fun requestLogin(model: LoginRequestModel) {
        scope.launch {
            try {
                getView()?.showLoading()
                model.numberPhone = standardizationNumberPhone(model.numberPhone)
                val auth = withContext(Dispatchers.IO) {
                    loginUseCase(model)
                }
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

    private fun storeData(model: AuthResponse){
    }


    override fun detachView() {
        presenterJob.cancel()
        super.detachView()
    }

}