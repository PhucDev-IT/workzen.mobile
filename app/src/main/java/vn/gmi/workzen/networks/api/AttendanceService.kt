package vn.gmi.workzen.networks.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
import vn.gmi.workzen.data.models.response.attendance.WorkSheetByMonthYearResModel
import vn.gmi.workzen.networks.models.ApiResponse

interface AttendanceService {
    @GET(EndPoints.GET_INFO_ATTENDANCE)
    suspend fun getWorkScheduleToday(@Path("accountId") accountId: String): Response<ApiResponse<GetWorkScheduleResModel?>>

    @POST(EndPoints.CHECK_IN)
    suspend fun checkIn(@Body req: CheckInRequestModel): Response<ApiResponse<GetWorkScheduleResModel?>>

    @POST(EndPoints.CHECK_OUT)
    suspend fun checkOut(@Body req: CheckoutReqModel): Response<ApiResponse<GetWorkScheduleResModel?>>

    @GET(EndPoints.REPORT_ATTENDANCE_USER_IN_MONTH)
    suspend fun reportAttendanceUserInMonth(@Query("month") month:Int, @Query("year") year:Int): Response<ApiResponse<WorkSheetByMonthYearResModel?>>
}