package vn.gmi.workzen.data.datasource.local.auth

import vn.gmi.workzen.data.models.auth.AuthResponse

interface  AuthLocalDataSource {
    suspend fun storeAuth(model: AuthResponse)
    suspend fun logout()
}