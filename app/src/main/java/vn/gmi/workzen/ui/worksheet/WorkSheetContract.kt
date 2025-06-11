package vn.gmi.workzen.ui.worksheet

import vn.gmi.workzen.core.base.BaseContract
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity

interface WorkSheetContract {
    interface View: BaseContract.View{
        fun onGetReportAttendanceByMonthYear(entity: ReportWorkSheetMonthYearEntity?)
    }

    interface Presenter: BaseContract.Presenter<View>{
        fun getReportAttendanceByMonthYear(month: Int, year: Int)
    }
}