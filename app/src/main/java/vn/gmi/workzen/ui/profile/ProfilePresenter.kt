package vn.gmi.workzen.ui.profile

import android.util.Log
import kotlinx.coroutines.launch
import vn.gmi.workzen.core.base.BasePresenter
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.domain.usecase.GetIdentificationUseCase
import vn.gmi.workzen.domain.usecase.GetProfileUseCase
import vn.gmi.workzen.domain.usecase.LogoutUseCase
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.utils.MySharedPreferences
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase
): BasePresenter<ProfileContract.View>(), ProfileContract.Presenter {

    override fun getProfile() {
        scope.launch {
            try {
                if(SessionManager.profile!=null){
                    getView()?.onGetProfileSuccess(SessionManager.profile!!)
                    return@launch
                }
                val userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_ACCOUNT_ID)
                val response = getProfileUseCase.invoke(userId?:"")
                if(response!=null){
                    getView()?.onGetProfileSuccess(response)
                }
            }catch (e: Exception){
                Log.e("getProfile",e.message?:"")
            }
        }
    }

    override fun logout() {
      scope.launch {   logoutUseCase.invoke() }
    }
}