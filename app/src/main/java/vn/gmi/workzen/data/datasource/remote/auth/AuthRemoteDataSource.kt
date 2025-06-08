package vn.gmi.workzen.data.datasource.remote.auth

import retrofit2.Response
import vn.gmi.workzen.data.models.request.auth.LoginRequestModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.data.models.response.auth.TokenResponse
import vn.gmi.workzen.networks.models.ApiResponse

interface AuthRemoteDataSource {
    suspend fun login(req: LoginRequestModel): Response<ApiResponse<LoginResponseModel>>
    suspend fun checkExistsPhone(phone: String): Response<ApiResponse<Boolean>>
    suspend fun register( request: Map<String,String>): Response<ApiResponse<Boolean>>
    suspend fun refreshToken(token:String): Response<ApiResponse<TokenResponse>>
    suspend fun logout()
    suspend fun verifyToken(token:String): Response<ApiResponse<Boolean>>
}