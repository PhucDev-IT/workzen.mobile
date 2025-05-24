package vn.gmi.workzen.data.datasource.remote.auth

import vn.gmi.workzen.data.apis.AuthenticationService
import vn.gmi.workzen.data.models.auth.LoginRequestModel
import vn.gmi.workzen.data.models.auth.AuthResponse

class AuthRemoteDataSourceImpl(private val apiService: AuthenticationService) :AuthRemoteDataSource{

    override suspend fun login(req: LoginRequestModel): AuthResponse {
        return apiService.requestLogin(req)
    }

    override suspend fun refreshToken(token:String): AuthResponse {
        return AuthResponse(null,"")
    }

    override suspend fun logout(){

    }
}