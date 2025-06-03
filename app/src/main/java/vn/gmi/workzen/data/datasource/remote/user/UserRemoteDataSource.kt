package vn.gmi.workzen.data.datasource.remote.user

import retrofit2.Response
import vn.gmi.workzen.data.models.request.user.OnboardUserReqModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.data.models.response.auth.OnboardUserResModel
import vn.gmi.workzen.data.models.response.user.ProfileResponseModel
import vn.gmi.workzen.domain.entity.ProfileEntity
import vn.gmi.workzen.networks.models.ApiResponse

interface UserRemoteDataSource {
   suspend fun updateIdentification(req: OnboardUserReqModel): Response<ApiResponse<OnboardUserResModel>>
   suspend fun getIdentification(userId: String): Response<ApiResponse<OnboardUserResModel>>
   suspend fun getProfile(userId: String): Response<ApiResponse<ProfileResponseModel>>
}