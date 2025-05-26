package vn.gmi.workzen.ui.authentication.signup

import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.extensions.standardizationNumberPhone
import vn.gmi.workzen.utils.Constants

class RegisterPresenter : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {
    override fun requestVerifyPhone(phone: String) {
        if(phone.isEmpty() || phone.length < Constants.MIN_LENGTH_PHONE || (!phone.startsWith("0") && !phone.startsWith("+84"))){
            getView()?.onError("Số điện thoại không hợp lệ")
            return
        }
        val  standardization = standardizationNumberPhone(phone)
        getView()?.navigateToStep(1)
    }

    override fun requestVerifyOTP(otp: String) {
        getView()?.navigateToStep(2)
    }

    override fun requestGetOTPCode() {

    }
}
