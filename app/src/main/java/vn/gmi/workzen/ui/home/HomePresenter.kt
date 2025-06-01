package vn.gmi.workzen.ui.home

import android.util.Log
import kotlinx.coroutines.launch
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.models.NewspaperModel
import vn.gmi.workzen.domain.usecase.GetIdentificationUseCase
import vn.gmi.workzen.utils.MySharedPreferences
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val getIdentificationUseCase: GetIdentificationUseCase
) : BasePresenter<HomeContract.View>(), HomeContract.Presenter{

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

    override fun checkOnboardUser() {
        scope.launch {
            try{
                val userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)
                val response = getIdentificationUseCase.invoke(userId?:"")
                getView()?.onCheckOnboardUser(response!=null && !response.eidNumber.isNullOrEmpty())
            }catch (e:Exception){
                getView()?.onCheckOnboardUser(false)
                Log.e("checkOnboardUser",e.message?:"")
            }
        }
    }
}