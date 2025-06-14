package vn.gmi.workzen.domain.entity.attendance

sealed class WorkDayItem {
    object EmptyDay : WorkDayItem()
    data class WorkDay(val data: ReportWorkSheetDayEntity) : WorkDayItem()
}
