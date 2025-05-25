package vn.gmi.workzen.networks.api

import retrofit2.http.Body
import retrofit2.http.POST
import vn.gmi.workzen.networks.models.request.LoginRequestModel
import vn.gmi.workzen.networks.models.response.auth.AuthenticationResponse

interface AuthenticationService {
    @POST(EndPoints.LOGIN_ENDPOINT)
    suspend fun requestLogin(@Body request: LoginRequestModel): AuthenticationResponse
}