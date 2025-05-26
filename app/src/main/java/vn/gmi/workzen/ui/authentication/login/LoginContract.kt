package vn.gmi.workzen.ui.authentication.login

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.networks.models.request.LoginRequestModel
import vn.gmi.workzen.networks.models.response.auth.LoginResponseModel

interface LoginContract {
    interface View : BaseContract.View{
        fun onLoginSuccess(model: LoginResponseModel)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun requestLogin(model: LoginRequestModel)
    }
}