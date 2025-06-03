package vn.gmi.workzen.ui.home.time_keeping

import android.util.Log
import kotlinx.coroutines.launch
import vn.gmi.workzen.MyApplication
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.domain.usecase.GetShiftByUserUseCase
import vn.gmi.workzen.manager.schedule.ReminderScheduler
import vn.gmi.workzen.ui.home.HomeContract
import vn.gmi.workzen.utils.MySharedPreferences
import javax.inject.Inject

class TimeKeepingPresenter @Inject constructor(
    private val getShiftByUserUseCase: GetShiftByUserUseCase
): BasePresenter<TimeKeepingContract.View>(), TimeKeepingContract.Presenter {
    override fun getShift() {
        scope.launch {
            try {
                val userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)
                val result = getShiftByUserUseCase.invoke(userId?:"")
                if(result!=null){
                    MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_SHIFT_START,result.startTime?:"")
                    MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_SHIFT_END,result.startTime?:"")

                    ReminderScheduler.scheduleAllIfNeeded(MyApplication.instance)
                }
            }catch (e: Exception){
                Log.e("TimeKeepingPresenter","Lôi: ${e.message}")
            }
        }
    }


}