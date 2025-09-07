package vn.gmi.workzen.data.datasource.local.attendance

import retrofit2.Response
import vn.gmi.workzen.data.models.response.attendance.StatisticSalaryOfYearRes
import vn.gmi.workzen.domain.entity.attendance.MonthlyWorkOverviewEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity
import vn.gmi.workzen.domain.entity.attendance.StatisticSalaryOfYearEntity
import vn.gmi.workzen.networks.models.ApiResponse

interface AttendanceLocalDataSource {
    suspend fun saveWorkSheetMonthYear(entity: ReportWorkSheetMonthYearEntity)
    suspend fun getWorkSheetMonthYear(id:String): ReportWorkSheetMonthYearEntity?
    suspend fun getReportWorkSheetTheDay(id:String): ReportWorkSheetDayEntity?

    suspend fun getReportMonthlyWorkOverview(month: Int, year: Int, userId: String): MonthlyWorkOverviewEntity?
    suspend fun getReportSalaryOfYear(year: Int) :StatisticSalaryOfYearEntity?
}