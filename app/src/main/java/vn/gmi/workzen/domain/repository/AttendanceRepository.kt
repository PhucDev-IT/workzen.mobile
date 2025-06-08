package vn.gmi.workzen.domain.repository

import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.request.attendance.InfoAttendanceParams
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel

interface AttendanceRepository {
    suspend fun checkIn(req: CheckInRequestModel): GetWorkScheduleResModel?
    suspend fun checkOut(req: CheckoutReqModel): GetWorkScheduleResModel?
    suspend fun getWorkScheduleTodayUseCase(params: String): GetWorkScheduleResModel?
}