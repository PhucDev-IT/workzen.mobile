package vn.gmi.workzen.domain.usecase

import kotlinx.coroutines.flow.Flow
import vn.gmi.workzen.core.usecases.BaseUseCase
import vn.gmi.workzen.domain.entity.notification.Notification
import vn.gmi.workzen.domain.repository.NotificationRepository


class GetNotificationRemoteUseCase(private val notificationRepository: NotificationRepository): BaseUseCase<String, List<Notification>>() {
    override suspend fun invoke(params: String): List<Notification> {
        return notificationRepository.getNotificationsRemote(params)
    }
}

class GetNotificationLocalUseCase(private val notificationRepository: NotificationRepository): BaseUseCase<String,List<Notification>>() {
    override suspend fun invoke(params: String): List<Notification>{
        return notificationRepository.getNotificationsLocal(params)
    }
}

class CountNotificationUseCase(private val notificationRepository: NotificationRepository): BaseUseCase<String, Long>() {
    override suspend fun invoke(params: String): Long {
        return notificationRepository.countNotifications(params)
    }
}

class MarkAsReadNotificationUseCase(private val notificationRepository: NotificationRepository): BaseUseCase<Notification, Unit>() {
    override suspend fun invoke(params: Notification) {
        return notificationRepository.markAsReadNotification(params)
    }
}