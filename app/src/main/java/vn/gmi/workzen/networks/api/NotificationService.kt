package vn.gmi.workzen.networks.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import vn.gmi.workzen.data.models.response.notification.NotificationResponseModel
import vn.gmi.workzen.networks.models.ApiResponse

interface NotificationService {

    @GET(EndPoints.GET_ALL_NOTIFICATION)
    suspend fun getAllNotification(@Query("accountId") accountId: String): Response<ApiResponse<List<NotificationResponseModel>>>

    @GET(EndPoints.COUNT_NOTIFICATION)
    suspend fun countNotification(@Query("accountId") accountId: String): Response<ApiResponse<Long>>

    @POST(EndPoints.MARK_AS_READ)
    suspend fun markAsRead(@Body notificationId:String): Response<ApiResponse<NotificationResponseModel>>

}