package vn.gmi.workzen.data.apis

import retrofit2.http.Body
import retrofit2.http.POST
import vn.gmi.workzen.data.models.auth.LoginRequestModel
import vn.gmi.workzen.networks.api.EndPoints
import vn.gmi.workzen.data.models.auth.AuthResponse

interface AuthenticationService {
    @POST(EndPoints.LOGIN_ENDPOINT)
    suspend fun requestLogin(@Body request: LoginRequestModel): AuthResponse
}