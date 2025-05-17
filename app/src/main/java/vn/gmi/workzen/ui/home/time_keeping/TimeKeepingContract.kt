package vn.gmi.workzen.ui.home.time_keeping

import vn.gmi.workzen.core.base.BaseContract

interface TimeKeepingContract {
    interface  View: BaseContract.View{

    }

    interface Presenter: BaseContract.Presenter<View>{
        fun getData()
    }
}