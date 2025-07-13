package vn.gmi.workzen.ui.home

import android.app.Application
import android.util.Log
import kotlinx.coroutines.launch
import vn.gmi.workzen.MyApplication
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.models.NewspaperModel
import vn.gmi.workzen.domain.entity.shift.ShiftEntity
import vn.gmi.workzen.domain.usecase.GetProfileLocalUseCase
import vn.gmi.workzen.domain.usecase.GetProfileRemoteUseCase
import vn.gmi.workzen.domain.usecase.StoreProfileUseCase
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.manager.schedule.ReminderScheduler
import vn.gmi.workzen.manager.schedule.ReminderScheduler.scheduleReminder
import vn.gmi.workzen.utils.FormatUtils
import vn.gmi.workzen.utils.MySharedPreferences
import javax.inject.Inject

class HomePresenter  : BasePresenter<HomeContract.View>(), HomeContract.Presenter {

    override fun getNotificationAndEvent() {
        val list = listOf(
            NewspaperModel(
                "https://epu.edu.vn/Uploads/images/2024/9(2).jpg",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"
            ),
            NewspaperModel(
                "https://epu.edu.vn/Uploads/images/2024/9(2).jpg",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"
            ),
            NewspaperModel(
                "https://epu.edu.vn/Uploads/images/2024/9(2).jpg",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"
            ),
            NewspaperModel(
                "https://epu.edu.vn/Uploads/images/2024/9(2).jpg",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"
            ),
            NewspaperModel(
                "https://epu.edu.vn/Uploads/images/2024/9(2).jpg",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"
            ),
            NewspaperModel(
                "https://epu.edu.vn/Uploads/images/2024/9(2).jpg",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"
            ),
            NewspaperModel(
                "https://epu.edu.vn/Uploads/images/2024/9(2).jpg",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975",
                "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"
            )
        )

        getView()?.onResultNotificationAndEvents(list)
    }

}