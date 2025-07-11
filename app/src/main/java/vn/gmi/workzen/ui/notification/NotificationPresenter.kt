package vn.gmi.workzen.ui.notification

import kotlinx.coroutines.launch
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.domain.entity.notification.Notification
import vn.gmi.workzen.domain.entity.notification.NotifyType
import vn.gmi.workzen.domain.usecase.GetNotificationLocalUseCase
import vn.gmi.workzen.domain.usecase.GetNotificationRemoteUseCase
import vn.gmi.workzen.domain.usecase.MarkAsReadNotificationUseCase
import vn.gmi.workzen.utils.MySharedPreferences
import javax.inject.Inject

class NotificationPresenter @Inject constructor(
    private val getNotificationRemoteUseCase: GetNotificationRemoteUseCase,
    private val getNotificationLocalUseCase: GetNotificationLocalUseCase,
    private val markAsReadNotificationUseCase: MarkAsReadNotificationUseCase
): BasePresenter<NotificationContract.View>(), NotificationContract.Presenter {

    override fun requestGetNotification() {
        scope.launch {
            try{
                getView()?.showLoading()
                val accountId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)
                val local = getNotificationLocalUseCase.invoke(accountId.toString())
                getView()?.onGetNotificationSuccess(local)
                val remote = getNotificationRemoteUseCase.invoke(accountId.toString())
                getView()?.onGetNotificationSuccess(remote)
            }catch (e: Exception){
                getView()?.onError(e.message?:"")
            }
            finally {
                getView()?.hideLoading()
            }
        }
    }

    override fun requestMarkAsRead(notification: Notification) {
       scope.launch {
           try{
               markAsReadNotificationUseCase.invoke(notification)
           }catch (e: Exception){
               getView()?.onError(e.message?:"")
           }
       }
    }

    private fun handleNotificationData(notifications: List<Notification>){
        val (notificationList, otherList) = notifications.partition { it.type == NotifyType.NOTIFICATION.name }

    }
}