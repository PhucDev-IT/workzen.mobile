package vn.gmi.workzen.domain.repository

import vn.gmi.workzen.data.models.request.auth.LoginRequestModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.data.models.response.auth.TokenResponse


interface AuthRepository {
    suspend fun login(request: LoginRequestModel): LoginResponseModel
    suspend fun checkExistsPhone(phone: String): Boolean
    suspend fun register(request: Map<String,String>): Boolean
    suspend fun logout()
    suspend fun refreshToken(token:String):TokenResponse
    suspend fun verifyToken(token:String):Boolean
}