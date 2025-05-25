package vn.gmi.workzen.ui.authentication.signup

import vn.gmi.workzen.core.base.BaseContract

interface RegisterContract {
    interface View:BaseContract.View{
        fun navigateToStep(step: Int)
    }

    interface ViewPhoneInput{

    }

    interface Presenter:BaseContract.Presenter<View>{
        fun requestVerifyPhone(phone:String)
    }
}