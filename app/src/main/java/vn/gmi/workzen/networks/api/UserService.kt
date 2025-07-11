package vn.gmi.workzen.networks.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import vn.gmi.workzen.networks.models.ApiResponse
import vn.gmi.workzen.data.models.request.user.OnboardUserReqModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.data.models.response.auth.OnboardUserResModel
import vn.gmi.workzen.data.models.response.user.ProfileResponseModel
import vn.gmi.workzen.data.models.response.user.UserResponseModel
import vn.gmi.workzen.domain.entity.user.ProfileEntity

interface UserService {
    @PUT(EndPoints.UPDATE_IDENTIFICATION_ENDPOINT)
    suspend fun requestUpdateIdentification(@Body request: OnboardUserReqModel): Response<ApiResponse<OnboardUserResModel>>


    @GET(EndPoints.GET_PROFILE)
    suspend fun requestGetProfile(@Query("userId") userId: String): Response<ApiResponse<ProfileResponseModel>>

}