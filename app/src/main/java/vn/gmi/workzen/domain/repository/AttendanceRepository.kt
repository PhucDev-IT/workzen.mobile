package vn.gmi.workzen.domain.repository

import retrofit2.Response
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.request.attendance.InfoAttendanceParams
import vn.gmi.workzen.data.models.response.attendance.AttendanceResModel
import vn.gmi.workzen.networks.models.ApiResponse
import java.time.LocalDate

interface AttendanceRepository {
    suspend fun checkIn(req: CheckInRequestModel): AttendanceResModel?
    suspend fun checkOut(req: CheckoutReqModel): AttendanceResModel?
    suspend fun getInfo(params: InfoAttendanceParams): AttendanceResModel?
}