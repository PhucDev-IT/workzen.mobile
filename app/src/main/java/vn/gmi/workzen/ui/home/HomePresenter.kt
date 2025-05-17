package vn.gmi.workzen.ui.home

import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.data.models.NewspaperModel

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter{

    override fun getNotificationAndEvent() {
        val list = listOf(
            NewspaperModel("https://epu.edu.vn/Uploads/images/2024/9(2).jpg", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"),
            NewspaperModel("https://epu.edu.vn/Uploads/images/2024/9(2).jpg", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"),
            NewspaperModel("https://epu.edu.vn/Uploads/images/2024/9(2).jpg", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"),
            NewspaperModel("https://epu.edu.vn/Uploads/images/2024/9(2).jpg", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"),
            NewspaperModel("https://epu.edu.vn/Uploads/images/2024/9(2).jpg", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"),
            NewspaperModel("https://epu.edu.vn/Uploads/images/2024/9(2).jpg", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975"),
            NewspaperModel("https://epu.edu.vn/Uploads/images/2024/9(2).jpg", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975", "Ý nghĩa lịch sử ngày Giải phóng Miền Nam thống nhất đất nước 30/4/1975")
        )

        getView()?.onResultNotificationAndEvents(list)
    }
}