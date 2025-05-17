package vn.gmi.workzen.ui.profile

import vn.gmi.workzen.core.base.BaseContract

interface ProfileContract {
    interface View:BaseContract.View{}

    interface Presenter:BaseContract.Presenter<View>{

    }
}