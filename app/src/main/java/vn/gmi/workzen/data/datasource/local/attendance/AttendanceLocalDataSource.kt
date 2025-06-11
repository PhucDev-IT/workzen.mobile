package vn.gmi.workzen.data.datasource.local.attendance

import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity

interface AttendanceLocalDataSource {
    suspend fun saveWorkSheetMonthYear(entity: ReportWorkSheetMonthYearEntity)
    suspend fun getWorkSheetMonthYear(id:String): ReportWorkSheetMonthYearEntity?
}