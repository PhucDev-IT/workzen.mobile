package vn.gmi.workzen.domain.repository

import vn.gmi.workzen.data.models.auth.LoginRequestModel
import vn.gmi.workzen.data.models.auth.AuthResponse

interface AuthRepository {
    suspend fun login(request: LoginRequestModel): AuthResponse
    suspend fun logout()
    suspend fun refreshToken(token:String)
}