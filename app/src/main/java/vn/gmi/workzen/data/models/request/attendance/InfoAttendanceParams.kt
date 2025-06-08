package vn.gmi.workzen.data.models.request.attendance

import java.time.LocalDate

data class InfoAttendanceParams(
    val accountId: String,
    val date: LocalDate
)