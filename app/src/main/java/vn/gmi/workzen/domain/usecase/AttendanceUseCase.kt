package vn.gmi.workzen.domain.usecase

import vn.gmi.workzen.core.usecases.BaseUseCase
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.request.attendance.InfoAttendanceParams
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
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
