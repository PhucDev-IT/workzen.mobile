package vn.gmi.workzen.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import vn.gmi.workzen.data.datasource.local.notification.NotificationLocalDataSource
import vn.gmi.workzen.data.datasource.remote.notification.NotificationRemoteDataSource
import vn.gmi.workzen.data.di.IoDispatcher
import vn.gmi.workzen.data.models.response.notification.NotificationResponseModel
import vn.gmi.workzen.domain.entity.notification.Notification
import vn.gmi.workzen.domain.repository.NotificationRepository
import vn.gmi.workzen.networks.rest.ApiResult
import vn.gmi.workzen.networks.rest.toApiResult

class NotificationRepositoryImpl(
    private val remoteDataSource: NotificationRemoteDataSource,
    private val localDataSource: NotificationLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): NotificationRepository {
    override suspend fun getNotificationsRemote(accountId: String): List<Notification> = withContext(ioDispatcher) {
      when(val result = remoteDataSource.getAllNotification(accountId).toApiResult()){
          is ApiResult.Success -> result.data.map { it.mapToEntity() }
          is ApiResult.Error -> throw Exception(result.message)
      }
    }

    override suspend fun getNotificationsLocal(accountId: String): List<Notification> {
      return withContext(ioDispatcher){
          localDataSource.getAll(accountId)
      }
    }

    override suspend fun countNotifications(accountId: String): Long = withContext(ioDispatcher) {
        when(val result = remoteDataSource.getAllNotification(accountId).toApiResult()){
            is ApiResult.Success -> result.data.size.toLong()
            is ApiResult.Error -> throw Exception(result.message)
        }
    }

    override suspend fun markAsReadNotification(notification: Notification) {
       withContext(ioDispatcher){
           localDataSource.update(notification)
            remoteDataSource.markAsRead(notification.id)
       }
    }
}