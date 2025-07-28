package vn.gmi.workzen.data.datasource.remote.attendance

import retrofit2.Response
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
import vn.gmi.workzen.data.models.response.attendance.MonthlyWorkOverviewRes
import vn.gmi.workzen.data.models.response.attendance.StatisticSalaryOfYearRes
import vn.gmi.workzen.data.models.response.attendance.WorkSheetByMonthYearResModel
import vn.gmi.workzen.networks.models.ApiResponse

interface AttendanceRemoteDataSource {
    suspend fun checkIn(req: CheckInRequestModel): Response<ApiResponse<GetWorkScheduleResModel?>>
    suspend fun checkOut(req: CheckoutReqModel): Response<ApiResponse<GetWorkScheduleResModel?>>
    suspend fun getReportWorkSheetInMonthYear(month: Int, year: Int): Response<ApiResponse<WorkSheetByMonthYearResModel?>>
    suspend fun getWorkScheduleToday(
        accountId: String,
    ): Response<ApiResponse<GetWorkScheduleResModel?>>

    suspend fun getMonthlyWorkOverview(month: Int, year: Int) : Response<ApiResponse<MonthlyWorkOverviewRes?>>
    suspend fun getReportSalaryOfYear(year: Int) : Response<ApiResponse<StatisticSalaryOfYearRes?>>
}