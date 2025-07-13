package vn.gmi.workzen.ui.main

import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import vn.gmi.workzen.MyApplication
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.domain.usecase.GetProfileLocalUseCase
import vn.gmi.workzen.domain.usecase.GetProfileRemoteUseCase
import vn.gmi.workzen.domain.usecase.StoreProfileUseCase
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.manager.schedule.ReminderScheduler
import vn.gmi.workzen.utils.MySharedPreferences
import javax.inject.Inject
import kotlin.invoke

class MainPresenter @Inject constructor(
    private val getProfileRemoteUseCase: GetProfileRemoteUseCase,
    private val getProfileLocalUseCase: GetProfileLocalUseCase,
    private val storeProfileUseCase: StoreProfileUseCase
) : BasePresenter<MainContract.View>(), MainContract.Presenter {
    override fun observeProfile() {
        scope.launch {
            try {
                val userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)

               val profile = getProfileRemoteUseCase.invoke(userId ?: "")
                if (profile != null) {
                    storeProfileUseCase.invoke(profile)
                    Log.d("Phuc", "Identfi: ${profile.details}")
                    MySharedPreferences.setBooleanValue(
                        SharedPreferenceKey.KEY_IS_ONBOARD,
                        profile.details?.isVerified == true
                    )
                    val contract = profile.contracts.first { it.isActive == true }

                    MySharedPreferences.setStringValue(
                        SharedPreferenceKey.KEY_SHIFT_START,
                        contract.shift?.startTime ?: ""
                    )
                    MySharedPreferences.setStringValue(
                        SharedPreferenceKey.KEY_SHIFT_END,
                        contract.shift?.endTime ?: ""
                    )
                    MySharedPreferences.setStringValue(
                        SharedPreferenceKey.KEY_SHIFT_ID,
                        contract.shift?.id ?: ""
                    )
                    ReminderScheduler.scheduleAllIfNeeded(MyApplication.instance)


                }
            } catch (e: Exception) {
                Log.e("checkOnboardUser", e.message ?: "")
            }
        }
    }
}