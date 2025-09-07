package vn.gmi.workzen.ui.notification

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.domain.entity.notification.Notification

interface NotificationContract {
    interface View: BaseContract.View{
        fun onGetNotificationSuccess(notifications: List<Notification>)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun requestGetNotification()
        fun requestMarkAsRead(notification: Notification)
    }
}