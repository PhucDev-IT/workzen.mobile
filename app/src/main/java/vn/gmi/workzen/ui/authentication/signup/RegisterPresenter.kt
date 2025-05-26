package vn.gmi.workzen.ui.authentication.signup

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.extensions.standardizationNumberPhone
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.rest.networkCallback
import vn.gmi.workzen.networks.rest.onError
import vn.gmi.workzen.networks.rest.onSuccess
import vn.gmi.workzen.utils.Constants

class RegisterPresenter : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {

    private var phoneCache = ""

    override fun requestVerifyPhone(phone: String) {
        if(phone.isEmpty() || phone.length < Constants.MIN_LENGTH_PHONE || (!phone.startsWith("0") && !phone.startsWith("+84"))){
            getView()?.onError("Số điện thoại không hợp lệ")
            return
        }
        scope.launch {
            try{
                getView()?.showLoading()
                val  standardization = standardizationNumberPhone(phone)

                networkCallback {
                    ApiService.instance.authenticationService.checkExistsPhone(standardization)
                }.onSuccess {
                    phoneCache = standardization
                    getView()?.navigateToStep(1)
                }.onError {
                    getView()?.onError(it)
                }

            }catch (e:Exception){
                getView()?.onError(e.message.toString())
            }finally {
                getView()?.hideLoading()
            }
        }
    }

    override fun register(password: String) {
        scope.launch {
            try{
                getView()?.showLoading()
                val maps = mapOf(
                    "phone" to phoneCache,
                    "password" to password
                )
                networkCallback {
                    ApiService.instance.authenticationService.register(maps)
                }.onSuccess {
                    getView()?.onRegisterSuccess()
                }.onError {
                    getView()?.onError(it)
                }

            }catch (e:Exception){
                getView()?.onError(e.message.toString())
            }finally {
                getView()?.hideLoading()
            }
        }
    }
}
