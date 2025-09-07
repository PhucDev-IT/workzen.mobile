package vn.gmi.workzen.ui.worksheet

import android.util.Log
import kotlinx.coroutines.launch
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.domain.usecase.ReportAttendanceByMonthYearLocalUseCase
import vn.gmi.workzen.domain.usecase.ReportAttendanceByMonthYearRemoteUseCase
import javax.inject.Inject

class WorkSheetPresenter @Inject constructor(
    private val reportAttendanceByMonthYearLocalUseCase: ReportAttendanceByMonthYearLocalUseCase,
    private val reportAttendanceByMonthYearRemoteUseCase: ReportAttendanceByMonthYearRemoteUseCase
): BasePresenter<WorkSheetContract.View>(),WorkSheetContract.Presenter {

    override fun getReportAttendanceByMonthYear(month: Int, year: Int) {
        scope.launch {
            try{
                val maps = mapOf("month" to month, "year" to year)
                val local = reportAttendanceByMonthYearLocalUseCase.invoke(maps)
                getView()?.onGetReportAttendanceByMonthYear(local)
                val remote = reportAttendanceByMonthYearRemoteUseCase.invoke(maps)
                getView()?.onGetReportAttendanceByMonthYear(remote)
            }catch (e: Exception){
                Log.e("WorkSheetPresenter",e.message.toString())
            }
        }
    }
}