package vn.gmi.workzen.networks.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import vn.gmi.workzen.networks.models.ApiResponse
import vn.gmi.workzen.data.models.request.auth.LoginRequestModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.data.models.request.auth.RefreshTokenRequest
import vn.gmi.workzen.data.models.response.auth.TokenResponse
import java.util.Objects

interface AuthenticationService {
    @POST(EndPoints.LOGIN_ENDPOINT)
    suspend fun requestLogin(@Body request: LoginRequestModel): Response<ApiResponse<LoginResponseModel>>

    @GET(EndPoints.CHECK_EXISTS_PHONE_ENDPOINT)
    suspend fun checkExistsPhone(@Query("phone") phone:String):Response<ApiResponse<Boolean>>

    @POST(EndPoints.REGISTER_ENDPOINT)
    suspend fun register(@Body request: Map<String,String>):Response<ApiResponse<Boolean>>

    @POST(EndPoints.REFRESH_TOKEN)
    suspend fun refreshToken(@Body map: Map<String,String>): Response<ApiResponse<TokenResponse>>
}