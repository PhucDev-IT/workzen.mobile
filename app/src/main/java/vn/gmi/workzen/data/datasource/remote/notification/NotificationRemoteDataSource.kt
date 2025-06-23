package vn.gmi.workzen.data.datasource.remote.notification

import retrofit2.Response
import vn.gmi.workzen.data.models.response.notification.NotificationResponseModel
import vn.gmi.workzen.networks.models.ApiResponse

interface NotificationRemoteDataSource {
    suspend fun getAllNotification(accountId: String): Response<ApiResponse<List<NotificationResponseModel>>>
    suspend fun countNotification(accountId: String): Response<ApiResponse<Long>>
    suspend fun markAsRead(notificationId:String): Response<ApiResponse<NotificationResponseModel>>

}