package vn.gmi.workzen.data.datasource.remote.attendance

import retrofit2.Response
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
import vn.gmi.workzen.networks.models.ApiResponse
import java.time.LocalDate

interface AttendanceRemoteDataSource {
    suspend fun checkIn(req: CheckInRequestModel): Response<ApiResponse<GetWorkScheduleResModel?>>
    suspend fun checkOut(req: CheckoutReqModel): Response<ApiResponse<GetWorkScheduleResModel?>>
    suspend fun getWorkScheduleToday(
        accountId: String,
    ): Response<ApiResponse<GetWorkScheduleResModel?>>
}