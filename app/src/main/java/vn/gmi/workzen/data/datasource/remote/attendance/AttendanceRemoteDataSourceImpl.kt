package vn.gmi.workzen.data.datasource.remote.attendance

import retrofit2.Response
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
import vn.gmi.workzen.data.models.response.attendance.WorkSheetByMonthYearResModel
import vn.gmi.workzen.networks.api.AttendanceService
import vn.gmi.workzen.networks.models.ApiResponse

class AttendanceRemoteDataSourceImpl(private val apiService: AttendanceService) : AttendanceRemoteDataSource{

    override suspend fun checkIn(req: CheckInRequestModel): Response<ApiResponse<GetWorkScheduleResModel?>> {
        return apiService.checkIn(req)
    }

    override suspend fun checkOut(req: CheckoutReqModel): Response<ApiResponse<GetWorkScheduleResModel?>> {
        return apiService.checkOut(req)
    }

    override suspend fun getWorkScheduleToday(accountId: String): Response<ApiResponse<GetWorkScheduleResModel?>> {
        return apiService.getWorkScheduleToday(accountId)
    }

    override suspend fun getReportWorkSheetInMonthYear(
        month: Int,
        year: Int
    ): Response<ApiResponse<WorkSheetByMonthYearResModel?>> {
        return apiService.reportAttendanceUserInMonth(month,year)
    }
}