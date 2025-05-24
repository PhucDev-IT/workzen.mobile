package vn.gmi.workzen.data.datasource.local.auth

import com.google.gson.Gson
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.models.auth.AuthResponse
import vn.gmi.workzen.utils.MySharedPreferences

class AuthLocalDataSourceImpl : AuthLocalDataSource {
    override suspend fun storeAuth(model: AuthResponse) {
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_USER_ID,model.user!!.id)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_FULL_NAME,model.user.fullName)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_PHONE,model.user.phone)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_EMAIL,model.user.email?:"")
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_AVATAR,model.user.avatar?:"")
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_ROLES, Gson().toJson(model.user.roles))
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_BEAR_ACCESS_TOKEN,model.bearToken)
        MySharedPreferences.setBooleanValue(SharedPreferenceKey.KEY_IS_LOGIN, true)
    }


    override suspend fun logout() {
        MySharedPreferences.removeKey(SharedPreferenceKey.KEY_USER_ID)
        MySharedPreferences.removeKey(SharedPreferenceKey.KEY_FULL_NAME)
        MySharedPreferences.removeKey(SharedPreferenceKey.KEY_PHONE)
        MySharedPreferences.removeKey(SharedPreferenceKey.KEY_EMAIL)
        MySharedPreferences.removeKey(SharedPreferenceKey.KEY_AVATAR)
        MySharedPreferences.removeKey(SharedPreferenceKey.KEY_ROLES)
        MySharedPreferences.removeKey(SharedPreferenceKey.KEY_BEAR_ACCESS_TOKEN)
        MySharedPreferences.removeKey(SharedPreferenceKey.KEY_IS_LOGIN)
    }
}