package vn.gmi.workzen.ui.authentication.login

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.networks.models.request.LoginRequestModel
import vn.gmi.workzen.networks.models.response.auth.AuthenticationResponse

interface LoginContract {
    interface View : BaseContract.View{
        fun onLoginSuccess(model: AuthenticationResponse)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun requestLogin(model: LoginRequestModel)
    }
}