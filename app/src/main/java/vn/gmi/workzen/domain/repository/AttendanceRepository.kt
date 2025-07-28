package vn.gmi.workzen.domain.repository

import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
import vn.gmi.workzen.domain.entity.attendance.MonthlyWorkOverviewEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity
import vn.gmi.workzen.domain.entity.attendance.StatisticSalaryOfYearEntity

interface AttendanceRepository {
    suspend fun checkIn(req: CheckInRequestModel): GetWorkScheduleResModel?
    suspend fun checkOut(req: CheckoutReqModel): GetWorkScheduleResModel?
    suspend fun getWorkScheduleTodayUseCase(params: String): GetWorkScheduleResModel?
    suspend fun reportAttendanceByMonthYearLocal(month: Int, year: Int): ReportWorkSheetMonthYearEntity?
    suspend fun reportAttendanceByMonthYearRemote(month: Int, year: Int): ReportWorkSheetMonthYearEntity?

    suspend fun getReportSalaryOfYearRemote(year: Int): StatisticSalaryOfYearEntity?
    suspend fun getReportSalaryOfYearLocal(year: Int): StatisticSalaryOfYearEntity?

    suspend fun getMonthlyWorkOverviewRemote(month: Int, year: Int): MonthlyWorkOverviewEntity?
    suspend fun getMonthlyWorkOverviewLocal(month: Int, year: Int): MonthlyWorkOverviewEntity?

}