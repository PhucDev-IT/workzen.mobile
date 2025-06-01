package vn.gmi.workzen.ui.authentication.signup

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.extensions.standardizationNumberPhone
import vn.gmi.workzen.domain.usecase.LoginUseCase
import vn.gmi.workzen.domain.usecase.PhoneExistsUseCase
import vn.gmi.workzen.domain.usecase.RegisterUseCase
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.rest.networkCallback
import vn.gmi.workzen.networks.rest.onError
import vn.gmi.workzen.networks.rest.onSuccess
import vn.gmi.workzen.utils.Constants
import javax.inject.Inject

class RegisterPresenter @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val checkExistsPhoneUseCase: PhoneExistsUseCase
) : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {

    private var phoneCache = ""

    override fun requestVerifyPhone(phone: String) {
        if (phone.isEmpty() || phone.length < Constants.MIN_LENGTH_PHONE || (!phone.startsWith("0") && !phone.startsWith(
                "+84"
            ))
        ) {
            getView()?.onError("Số điện thoại không hợp lệ")
            return
        }
        scope.launch {
            try {
                getView()?.showLoading()
                val standardization = standardizationNumberPhone(phone)

                val response = checkExistsPhoneUseCase.invoke(standardization)
                if(!response){
                    getView()?.onError("Số điện thoại đã tồn tại")
                    return@launch
                }
                phoneCache = standardization
                getView()?.navigateToStep(1)

            } catch (e: Exception) {
                getView()?.onError(e.message.toString())
            } finally {
                getView()?.hideLoading()
            }
        }
    }

    override fun register(password: String) {
        scope.launch {
            try {
                getView()?.showLoading()
                val maps = mapOf(
                    "phone" to phoneCache,
                    "password" to password
                )
                val response =  registerUseCase.invoke(maps)

                getView()?.onRegisterSuccess()

            } catch (e: Exception) {
                getView()?.onError(e.message.toString())
            } finally {
                getView()?.hideLoading()
            }
        }
    }
}
