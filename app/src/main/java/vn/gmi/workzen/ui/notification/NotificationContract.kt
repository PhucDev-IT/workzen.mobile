package vn.gmi.workzen.ui.notification

import vn.gmi.workzen.core.base.BaseContract

interface NotificationContract {
    interface View: BaseContract.View{

    }

    interface Presenter: BaseContract.Presenter<View>{

    }
}