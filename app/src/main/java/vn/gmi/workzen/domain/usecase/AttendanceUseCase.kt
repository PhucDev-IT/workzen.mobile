package vn.gmi.workzen.domain.usecase

import vn.gmi.workzen.core.usecases.BaseUseCase
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.request.attendance.InfoAttendanceParams
import vn.gmi.workzen.data.models.response.attendance.AttendanceResModel
import vn.gmi.workzen.domain.repository.AttendanceRepository
import java.time.LocalDate
import java.util.Objects

class GetInfoAttendanceUseCase(private val attendanceRepository: AttendanceRepository): BaseUseCase<InfoAttendanceParams, AttendanceResModel?>() {
    override suspend fun invoke(params: InfoAttendanceParams): AttendanceResModel? {
        return attendanceRepository.getInfo(params)
    }
}

class CheckInUseCase(private val attendanceRepository: AttendanceRepository): BaseUseCase<CheckInRequestModel, AttendanceResModel?>() {
    override suspend fun invoke(params: CheckInRequestModel): AttendanceResModel? {
        return attendanceRepository.checkIn(params)
    }
}

class CheckOutUseCase(private val attendanceRepository: AttendanceRepository): BaseUseCase<CheckoutReqModel, AttendanceResModel?>() {
    override suspend fun invoke(params: CheckoutReqModel): AttendanceResModel? {
       return attendanceRepository.checkOut(params)
    }
}
