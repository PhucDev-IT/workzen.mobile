package vn.gmi.workzen.ui.notification

import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.domain.usecase.GetNotificationLocalUseCase
import vn.gmi.workzen.domain.usecase.GetNotificationRemoteUseCase
import vn.gmi.workzen.domain.usecase.MarkAsReadNotificationUseCase
import javax.inject.Inject

class NotificationPresenter @Inject constructor(
    private val getNotificationRemoteUseCase: GetNotificationRemoteUseCase,
    private val getNotificationLocalUseCase: GetNotificationLocalUseCase,
    private val markAsReadNotificationUseCase: MarkAsReadNotificationUseCase
): BasePresenter<NotificationContract.View>(), NotificationContract.Presenter {
}