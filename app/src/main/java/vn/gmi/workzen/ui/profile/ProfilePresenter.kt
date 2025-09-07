package vn.gmi.workzen.ui.profile

import android.util.Log
import kotlinx.coroutines.launch
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.domain.usecase.GetMonthlyWorkOverviewLocalUseCase
import vn.gmi.workzen.domain.usecase.GetMonthlyWorkOverviewRemoteUseCase
import vn.gmi.workzen.domain.usecase.GetProfileUseCase
import vn.gmi.workzen.domain.usecase.LogoutUseCase
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.services.MyFirebaseService
import vn.gmi.workzen.utils.MySharedPreferences
import java.time.LocalDate
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val getMonthlyWorkOverviewLocalUseCase: GetMonthlyWorkOverviewLocalUseCase,
    private val  getMonthlyWorkOverviewRemoteUseCase: GetMonthlyWorkOverviewRemoteUseCase,
    private val logoutUseCase: LogoutUseCase
): BasePresenter<ProfileContract.View>(), ProfileContract.Presenter {

    override fun getMonthlyWorkOverview() {
        scope.launch {
            try {
                val now  = LocalDate.now()
                val maps = mapOf(
                    "month" to now.monthValue,
                    "year" to now.year
                )
                val local = getMonthlyWorkOverviewLocalUseCase.invoke(maps)
                local?.let { getView()?.onMonthlyWorkOverview(it) }

                val remote = getMonthlyWorkOverviewRemoteUseCase.invoke(maps)
                remote?.let { getView()?.onMonthlyWorkOverview(it) }

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    override fun logout() {
      scope.launch {
          logoutUseCase.invoke()
          MyFirebaseService.unsubscribeFromTopic()
      }
    }
}