package vn.gmi.workzen.ui.authentication.login

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.data.models.auth.LoginRequestModel
import vn.gmi.workzen.data.models.auth.AuthResponse

interface LoginContract {
    interface View : BaseContract.View{
        fun onLoginSuccess(model: AuthResponse)
        fun onError(message:String)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun requestLogin(model: LoginRequestModel)
    }
}