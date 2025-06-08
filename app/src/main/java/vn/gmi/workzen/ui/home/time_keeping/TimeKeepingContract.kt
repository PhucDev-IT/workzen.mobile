package vn.gmi.workzen.ui.home.time_keeping

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel

interface TimeKeepingContract {
    interface  View: BaseContract.View{
        fun onGetWorkScheduleSuccess(attendance: GetWorkScheduleResModel)
        fun onAttendanceSuccess(attendance: GetWorkScheduleResModel)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun getInfoAttendance()
        fun checkIn()
        fun checkOut()
    }
}