package vn.gmi.workzen.data.datasource.local.auth

import com.google.gson.Gson
import io.realm.kotlin.Realm
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.utils.MySharedPreferences


class AuthLocalDataSourceImpl : AuthLocalDataSource {
    override suspend fun storeAuth(model: LoginResponseModel) {
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_USER_ID, model.id)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_FULL_NAME,model.fullName)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_PHONE,model.phone)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_EMAIL,model.email?:"")
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_ROLES,Gson().toJson(model.roles))
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_BEAR_ACCESS_TOKEN,model.bearTokens.accessToken)
        MySharedPreferences.setStringValue(SharedPreferenceKey.KEY_REFRESH_TOKEN,model.bearTokens.refreshToken)
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
        MySharedPreferences.removeKey(SharedPreferenceKey.KEY_REFRESH_TOKEN)
        MySharedPreferences.removeKey(SharedPreferenceKey.KEY_IS_LOGIN)

        RealmProvider.realm.write {
          for(claz in RealmProvider.schemaModels){
              delete(claz)
          }
        }
    }
}