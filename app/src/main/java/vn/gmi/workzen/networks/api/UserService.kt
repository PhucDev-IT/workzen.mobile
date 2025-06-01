package vn.gmi.workzen.networks.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import vn.gmi.workzen.networks.models.ApiResponse
import vn.gmi.workzen.data.models.request.user.OnboardUserReqModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.data.models.response.auth.OnboardUserResModel

interface UserService {
    @POST(EndPoints.UPDATE_IDENTIFICATION_ENDPOINT)
    suspend fun requestUpdateIdentification(@Body request: OnboardUserReqModel): Response<ApiResponse<OnboardUserResModel>>

    @GET(EndPoints.GET_IDENTIFICATION_ENDPOINT)
    suspend fun requestGetIdentification(@Query("userId") userId: String): Response<ApiResponse<OnboardUserResModel>>
}