package vn.gmi.workzen.data.datasource.remote.attendance

import retrofit2.Response
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.response.attendance.AttendanceResModel
import vn.gmi.workzen.networks.models.ApiResponse
import java.time.LocalDate

interface AttendanceRemoteDataSource {
    suspend fun checkIn(req: CheckInRequestModel): Response<ApiResponse<AttendanceResModel?>>
    suspend fun checkOut(req: CheckoutReqModel): Response<ApiResponse<AttendanceResModel?>>
    suspend fun getInfo(
        accountId: String,
        date: LocalDate
    ): Response<ApiResponse<AttendanceResModel?>>
}