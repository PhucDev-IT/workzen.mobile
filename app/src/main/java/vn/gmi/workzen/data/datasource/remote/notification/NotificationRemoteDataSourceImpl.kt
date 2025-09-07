package vn.gmi.workzen.data.datasource.remote.notification

import retrofit2.Response
import vn.gmi.workzen.data.models.response.notification.NotificationResponseModel
import vn.gmi.workzen.networks.api.NotificationService
import vn.gmi.workzen.networks.models.ApiResponse

class NotificationRemoteDataSourceImpl(private val apiService: NotificationService) : NotificationRemoteDataSource {
    override suspend fun getAllNotification(accountId: String): Response<ApiResponse<List<NotificationResponseModel>>> {
        return apiService.getAllNotification(accountId)
    }

    override suspend fun countNotification(accountId: String): Response<ApiResponse<Long>> {
        return apiService.countNotification(accountId)
    }

    override suspend fun markAsRead(notificationId: String): Response<ApiResponse<NotificationResponseModel>> {
       return apiService.markAsRead(notificationId)
    }
}