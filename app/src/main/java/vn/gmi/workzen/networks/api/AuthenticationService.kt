package vn.gmi.workzen.networks.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import vn.gmi.workzen.networks.models.ApiResponse
import vn.gmi.workzen.networks.models.request.LoginRequestModel
import vn.gmi.workzen.networks.models.response.auth.LoginResponseModel

interface AuthenticationService {
    @POST(EndPoints.LOGIN_ENDPOINT)
    suspend fun requestLogin(@Body request: LoginRequestModel): Response<ApiResponse<LoginResponseModel>>

    @GET(EndPoints.CHECK_EXISTS_PHONE_ENDPOINT)
    suspend fun checkExistsPhone(@Query("phone") phone:String):Response<ApiResponse<Boolean>>

    @POST(EndPoints.REGISTER_ENDPOINT)
    suspend fun register(@Body request: Map<String,String>):Response<ApiResponse<Boolean>>
}