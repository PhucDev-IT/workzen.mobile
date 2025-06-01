package vn.gmi.workzen.ui.authentication.login

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.core.extensions.standardizationNumberPhone
import vn.gmi.workzen.data.models.request.auth.LoginRequestModel
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.domain.usecase.LoginUseCase
import vn.gmi.workzen.networks.rest.networkCallback
import vn.gmi.workzen.networks.rest.onError
import vn.gmi.workzen.networks.rest.onSuccess
import vn.gmi.workzen.utils.MySharedPreferences
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun requestLogin(model: LoginRequestModel) {
        scope.launch {
            try {
                getView()?.showLoading()
                model.numberPhone = standardizationNumberPhone(model.numberPhone)

                val response = loginUseCase.invoke(model)
                getView()?.onLoginSuccess(response)
            } catch (e: Exception) {
                getView()?.onError(e.message ?: "Unknown error")
            } finally {
                getView()?.hideLoading()
            }
        }

    }


    override fun detachView() {
        super.detachView()
    }

}