package vn.gmi.workzen.data.datasource.local.notification

import vn.gmi.workzen.domain.entity.notification.Notification

interface NotificationLocalDataSource {
    suspend fun getAll(accountId:String): List<Notification>
    suspend fun addAll(notifications: List<Notification>)
    suspend fun update(notification: Notification)
    suspend fun delete(notification: Notification)
}