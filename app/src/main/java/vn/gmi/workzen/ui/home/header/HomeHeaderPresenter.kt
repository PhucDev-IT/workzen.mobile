package vn.gmi.workzen.ui.home.header

import vn.gmi.workzen.core.base.BasePresenter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale

class HomeHeaderPresenter : BasePresenter<HomeHeaderContract.View>(), HomeHeaderContract.Presenter {
    override fun getTodayInfo() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, dd/MM/yyyy", Locale("vi"))
        val time = dateFormat.format(calendar.time) // Ví dụ: "Chủ nhật, 01/06/2025"
        getView()?.onShowTodayInfo(time)
    }
}