package vn.gmi.workzen.data.models.response.attendance

import java.time.LocalDateTime


class GetWorkScheduleResModel {
    var companyId: String? = null
    var workDate: String? = null
    var shifts: List<ShiftWorkInfo>? = null

    class ShiftWorkInfo {
         val shiftId: String? = null
         val shiftName: String? = null
         val startTime: String? = null
         val endTime: String? = null
         val checkedIn = false
         val checkedOut = false
         val isOverTime = false
         val checkInTime: LocalDateTime? = null
         val checkOutTime: LocalDateTime? = null
    }
}