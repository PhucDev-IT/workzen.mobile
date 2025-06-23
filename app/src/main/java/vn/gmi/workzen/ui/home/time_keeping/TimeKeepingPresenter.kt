package vn.gmi.workzen.ui.home.time_keeping

import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import android.util.Log
import kotlinx.coroutines.launch
import vn.gmi.workzen.MyApplication
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.request.attendance.InfoAttendanceParams
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
import vn.gmi.workzen.domain.usecase.CheckInUseCase
import vn.gmi.workzen.domain.usecase.CheckOutUseCase

import vn.gmi.workzen.domain.usecase.GetWorkScheduleTodayUseCase
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.manager.schedule.ReminderScheduler
import vn.gmi.workzen.ui.home.HomeContract
import vn.gmi.workzen.ui.home.models.ItemKeepingModel
import vn.gmi.workzen.utils.DateUtils
import vn.gmi.workzen.utils.MySharedPreferences
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class TimeKeepingPresenter @Inject constructor(
    private val getWorkScheduleTodayUseCase: GetWorkScheduleTodayUseCase,
    private val checkInUseCase: CheckInUseCase,
    private val checkOutUseCase: CheckOutUseCase,
) : BasePresenter<TimeKeepingContract.View>(), TimeKeepingContract.Presenter {

    override fun getInfoAttendance() {
        scope.launch {
            try {
                val accountId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_ACCOUNT_ID)
                val result =
                    getWorkScheduleTodayUseCase.invoke(accountId ?: "")
                if(result!= null){
                    getView()?.onGetWorkScheduleSuccess(result)
                }
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }
    }

    @SuppressLint("HardwareIds")
    override fun checkIn() {
        scope.launch {
            try {
                getView()?.showLoading()
                val shiftId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_SHIFT_ID)

                val localDate: LocalDate = LocalDate.now()
                val localDateTime: LocalDateTime = LocalDateTime.now()

                val deviceId = Settings.Secure.getString(MyApplication.instance.contentResolver, Settings.Secure.ANDROID_ID)
                val model = Build.MODEL       // Ví dụ: "Galaxy S22"

                val req = CheckInRequestModel(shiftId.toString(), DateUtils.formatLocalDate(localDate,
                    DateUtils.YEARMONTHDATFORMAT).toString(), DateUtils.formatLocalDateTime(localDateTime,
                    DateUtils.ISO8601DATEFORMAT).toString(),"","$deviceId|$model")

                val result = checkInUseCase.invoke(req)
                if(result!=null){
                    getView()?.onAttendanceSuccess(result)
                }
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }finally {
                getView()?.hideLoading()
            }
        }
    }

    @SuppressLint("HardwareIds")
    override fun checkOut() {
        scope.launch {
            try {
                getView()?.showLoading()
                val shiftId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_SHIFT_ID)

                val localDate: LocalDate = LocalDate.now()
                val localDateTime: LocalDateTime = LocalDateTime.now()

                val deviceId = Settings.Secure.getString(MyApplication.instance.contentResolver, Settings.Secure.ANDROID_ID)
                val model = Build.MODEL       // Ví dụ: "Galaxy S22"

                val req = CheckoutReqModel(shiftId.toString(), DateUtils.formatLocalDate(localDate,
                    DateUtils.YEARMONTHDATFORMAT).toString(), DateUtils.formatLocalDateTime(localDateTime,
                    DateUtils.ISO8601DATEFORMAT).toString(),"","$deviceId|$model")

                val result = checkOutUseCase.invoke(req)
                if(result!=null){
                    getView()?.onAttendanceSuccess(result)
                }
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }finally {
                getView()?.hideLoading()
            }
        }
    }





}