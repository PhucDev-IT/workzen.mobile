package vn.gmi.workzen.domain.usecase

import vn.gmi.workzen.core.usecases.BaseUseCase
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
import vn.gmi.workzen.domain.entity.attendance.MonthlyWorkOverviewEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity
import vn.gmi.workzen.domain.entity.attendance.StatisticSalaryOfYearEntity
import vn.gmi.workzen.domain.repository.AttendanceRepository

class GetWorkScheduleTodayUseCase(private val attendanceRepository: AttendanceRepository): BaseUseCase<String, GetWorkScheduleResModel?>() {
    override suspend fun invoke(params: String): GetWorkScheduleResModel? {
        return attendanceRepository.getWorkScheduleTodayUseCase(params)
    }
}

class CheckInUseCase(private val attendanceRepository: AttendanceRepository): BaseUseCase<CheckInRequestModel, GetWorkScheduleResModel?>() {
    override suspend fun invoke(params: CheckInRequestModel): GetWorkScheduleResModel? {
        return attendanceRepository.checkIn(params)
    }
}

class CheckOutUseCase(private val attendanceRepository: AttendanceRepository): BaseUseCase<CheckoutReqModel, GetWorkScheduleResModel?>() {
    override suspend fun invoke(params: CheckoutReqModel): GetWorkScheduleResModel? {
       return attendanceRepository.checkOut(params)
    }
}

class ReportAttendanceByMonthYearLocalUseCase(private val attendanceRepository: AttendanceRepository): BaseUseCase<Map<String,Int>, ReportWorkSheetMonthYearEntity?>(){
    override suspend fun invoke(params: Map<String, Int>): ReportWorkSheetMonthYearEntity? {
        return attendanceRepository.reportAttendanceByMonthYearLocal(params["month"]?:0, params["year"]?:0)
    }
}

class ReportAttendanceByMonthYearRemoteUseCase(private val attendanceRepository: AttendanceRepository): BaseUseCase<Map<String,Int>, ReportWorkSheetMonthYearEntity?>(){
    override suspend fun invoke(params: Map<String, Int>): ReportWorkSheetMonthYearEntity? {
        return attendanceRepository.reportAttendanceByMonthYearRemote(params["month"]?:0, params["year"]?:0)
    }
}

class GetMonthlyWorkOverviewLocalUseCase(private val attendanceRepository: AttendanceRepository):  BaseUseCase<Map<String,Int>, MonthlyWorkOverviewEntity?>(){
    override suspend fun invoke(params: Map<String, Int>): MonthlyWorkOverviewEntity? {
        return attendanceRepository.getMonthlyWorkOverviewLocal(params["month"]?:0, params["year"]?:0)
    }
}

class GetMonthlyWorkOverviewRemoteUseCase(private val attendanceRepository: AttendanceRepository):  BaseUseCase<Map<String,Int>, MonthlyWorkOverviewEntity?>(){
    override suspend fun invoke(params: Map<String, Int>): MonthlyWorkOverviewEntity? {
        return attendanceRepository.getMonthlyWorkOverviewRemote(params["month"]?:0, params["year"]?:0)
    }
}

class GetReportSalaryOfYearRemoteUseCase(private val attendanceRepository: AttendanceRepository):  BaseUseCase<Int, StatisticSalaryOfYearEntity?>(){
    override suspend fun invoke(params: Int): StatisticSalaryOfYearEntity? {
        return attendanceRepository.getReportSalaryOfYearRemote(params)
    }
}

class GetReportSalaryOfYearLocalUseCase(private val attendanceRepository: AttendanceRepository):  BaseUseCase<Int, StatisticSalaryOfYearEntity?>(){
    override suspend fun invoke(params: Int): StatisticSalaryOfYearEntity? {
        return attendanceRepository.getReportSalaryOfYearLocal(params)
    }
}

class GetReportWorkSheetTheDayLocalUseCase(private val attendanceRepository: AttendanceRepository):  BaseUseCase<String, ReportWorkSheetDayEntity?>(){
    override suspend fun invoke(params: String): ReportWorkSheetDayEntity? {
        return  attendanceRepository.getReportWorkSheetTheDayLocal(params)
    }
}