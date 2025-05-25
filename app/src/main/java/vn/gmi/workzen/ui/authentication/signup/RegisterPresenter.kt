package vn.gmi.workzen.ui.authentication.signup

import vn.gmi.workzen.core.base.BasePresenter

class RegisterPresenter : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {
    override fun requestVerifyPhone(phone: String) {
        getView()?.navigateToStep(1)
    }
}