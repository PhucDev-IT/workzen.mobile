package vn.gmi.workzen.ui.home

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.data.models.NewspaperModel
import vn.gmi.workzen.domain.entity.user.ProfileEntity

interface HomeContract {
    interface View : BaseContract.View {
        fun onResultNotificationAndEvents(items:List<NewspaperModel>)
        fun onGetProfile(model: ProfileEntity)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun getNotificationAndEvent()
        fun getProfile()
    }
}