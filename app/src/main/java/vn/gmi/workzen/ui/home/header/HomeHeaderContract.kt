package vn.gmi.workzen.ui.home.header

import vn.gmi.workzen.core.base.BaseContract

interface HomeHeaderContract {
    interface View : BaseContract.View {
        fun onShowTodayInfo(time:String)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun getTodayInfo()
    }
}