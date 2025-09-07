package vn.gmi.workzen.domain.repository

import kotlinx.coroutines.flow.Flow
import vn.gmi.workzen.data.models.response.notification.NotificationResponseModel
import vn.gmi.workzen.domain.entity.notification.Notification

interface NotificationRepository {
    suspend fun getNotificationsRemote(accountId: String): List<Notification>
    suspend fun getNotificationsLocal(accountId: String): List<Notification>
    suspend fun countNotifications(accountId: String): Long
    suspend fun markAsReadNotification(notification: Notification)

}