package vn.gmi.workzen.data.datasource.local.auth

import vn.gmi.workzen.data.models.response.auth.LoginResponseModel

interface  AuthLocalDataSource {
    suspend fun storeAuth(model: LoginResponseModel)
    suspend fun logout()
}