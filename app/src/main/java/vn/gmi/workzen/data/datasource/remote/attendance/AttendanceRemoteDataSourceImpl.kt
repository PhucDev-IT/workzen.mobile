package vn.gmi.workzen.data.datasource.remote.attendance

import retrofit2.Response
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.response.attendance.AttendanceResModel
import vn.gmi.workzen.networks.api.AttendanceService
import vn.gmi.workzen.networks.models.ApiResponse
import java.time.LocalDate

class AttendanceRemoteDataSourceImpl(private val apiService: AttendanceService) : AttendanceRemoteDataSource{

    override suspend fun checkIn(req: CheckInRequestModel): Response<ApiResponse<AttendanceResModel?>> {
        return apiService.checkIn(req)
    }

    override suspend fun checkOut(req: CheckoutReqModel): Response<ApiResponse<AttendanceResModel?>> {
        return apiService.checkOut(req)
    }

    override suspend fun getInfo(
        accountId: String,
        date: LocalDate
    ): Response<ApiResponse<AttendanceResModel?>> {
        return apiService.getInfoAttendance(accountId,date.toString())
    }
}