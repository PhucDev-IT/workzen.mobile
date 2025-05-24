package vn.gmi.workzen.data.datasource.remote.auth

import vn.gmi.workzen.data.models.auth.LoginRequestModel
import vn.gmi.workzen.data.models.auth.AuthResponse

interface AuthRemoteDataSource {
    suspend fun login(req: LoginRequestModel): AuthResponse
    suspend fun refreshToken(token:String): AuthResponse
    suspend fun logout()
}