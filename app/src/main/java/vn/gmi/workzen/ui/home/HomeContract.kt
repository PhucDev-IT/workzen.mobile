package vn.gmi.workzen.ui.home

import vn.gmi.workzen.base.BaseContract
import vn.gmi.workzen.data.models.NewspaperModel

interface HomeContract {
    interface View : BaseContract.View {
        fun onResultNotificationAndEvents(items:List<NewspaperModel>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getNotificationAndEvent()
    }
}