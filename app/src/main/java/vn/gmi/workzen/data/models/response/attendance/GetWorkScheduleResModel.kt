package vn.gmi.workzen.data.models.response.attendance

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime


data class GetWorkScheduleResModel(
    var companyId: String? = null,
    var workDate: String? = null,
    var shifts: List<ShiftWorkInfo>? = null
)
data class ShiftWorkInfo(
    var shiftId: String? = null,
    var shiftName: String? = null,
    var startTime: String? = null,
    var endTime: String? = null,
    var checkedIn: Boolean = false,
    var checkedOut: Boolean = false,
    var overTime: Boolean = false,
    var checkInTime: String? = null,
    var checkOutTime: String? = null
)
