package vn.gmi.workzen.data.models.request.attendance

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class CheckInRequestModel (
    var shiftId:String,
    var date: String,
    var checkInAt: String,
    var location:String,
    var deviceInfo:String
)