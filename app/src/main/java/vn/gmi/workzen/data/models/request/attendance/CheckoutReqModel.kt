package vn.gmi.workzen.data.models.request.attendance

import java.time.LocalDate
import java.time.LocalDateTime

data class CheckoutReqModel (
    var shiftId:String,
    var date: String,
    var checkOutAt: String,
    var location: String,
    var deviceInfo: String,
)