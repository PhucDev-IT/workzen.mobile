package vn.gmi.workzen.networks.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.response.attendance.AttendanceResModel
import vn.gmi.workzen.networks.models.ApiResponse

interface AttendanceService {
    @GET(EndPoints.GET_INFO_ATTENDANCE)
    suspend fun getInfoAttendance(@Path("accountId") accountId: String, @Query("date") date: String): Response<ApiResponse<AttendanceResModel?>>

    @POST(EndPoints.CHECK_IN)
    suspend fun checkIn(@Body req: CheckInRequestModel): Response<ApiResponse<AttendanceResModel?>>

    @POST(EndPoints.CHECK_OUT)
    suspend fun checkOut(@Body req: CheckoutReqModel): Response<ApiResponse<AttendanceResModel?>>
}