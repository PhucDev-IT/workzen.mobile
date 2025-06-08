package vn.gmi.workzen.ui.home.time_keeping

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.data.models.response.attendance.AttendanceResModel
import vn.gmi.workzen.domain.entity.shift.ShiftEntity

interface TimeKeepingContract {
    interface  View: BaseContract.View{
        fun onGetInfoAttendance(attendance: AttendanceResModel)
        fun onAttendanceSuccess(attendance: AttendanceResModel)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun getInfoAttendance()
        fun checkIn()
        fun checkOut()
    }
}