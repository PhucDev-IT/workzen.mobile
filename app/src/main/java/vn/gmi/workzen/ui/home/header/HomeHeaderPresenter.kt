package vn.gmi.workzen.ui.home.header

import kotlinx.coroutines.launch
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.domain.usecase.CountNotificationUseCase
import vn.gmi.workzen.utils.MySharedPreferences
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class HomeHeaderPresenter @Inject constructor(
    private val countNotificationUseCase: CountNotificationUseCase
): BasePresenter<HomeHeaderContract.View>(), HomeHeaderContract.Presenter {
    override fun getTodayInfo() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEEE, dd/MM/yyyy", Locale("vi"))
        val time = dateFormat.format(calendar.time) // Ví dụ: "Chủ nhật, 01/06/2025"
        getView()?.onShowTodayInfo(time)
    }

    override fun countNotification() {
        val accountId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_ACCOUNT_ID).toString()
        scope.launch {
            try{
                val count = countNotificationUseCase.invoke(accountId)
                getView()?.onCountNotification(count)
            }catch (e: Exception){}
        }
    }
}