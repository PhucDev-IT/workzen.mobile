package vn.gmi.workzen.data.datasource.remote.auth

import retrofit2.Response
import vn.gmi.workzen.data.models.request.auth.LoginRequestModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.data.models.response.auth.TokenResponse
import vn.gmi.workzen.networks.api.AuthenticationService
import vn.gmi.workzen.networks.models.ApiResponse


class AuthRemoteDataSourceImpl(private val apiService: AuthenticationService) :AuthRemoteDataSource{

    override suspend fun login(req: LoginRequestModel): Response<ApiResponse<LoginResponseModel>> {
        return apiService.requestLogin(req)
    }

    override suspend fun refreshToken(token:String): Response<ApiResponse<TokenResponse>> {
        val map = mapOf("refreshToken" to token)
        return apiService.refreshToken(map)
    }

    override suspend fun checkExistsPhone(phone: String): Response<ApiResponse<Boolean>> {
        return apiService.checkExistsPhone(phone)
    }

    override suspend fun register(request: Map<String, String>): Response<ApiResponse<Boolean>> {
       return apiService.register(request)
    }

    override suspend fun logout(){

    }

    override suspend fun verifyToken(token: String): Response<ApiResponse<Boolean>> {
        return apiService.verifyToken(token)
    }
}