package vn.gmi.workzen.data.datasource.remote.user

import retrofit2.Response
import vn.gmi.workzen.data.models.request.user.OnboardUserReqModel
import vn.gmi.workzen.data.models.response.auth.OnboardUserResModel
import vn.gmi.workzen.data.models.response.user.ProfileResponseModel
import vn.gmi.workzen.domain.entity.ProfileEntity
import vn.gmi.workzen.networks.api.AuthenticationService
import vn.gmi.workzen.networks.api.UserService
import vn.gmi.workzen.networks.models.ApiResponse

class UserRemoteDataSourceImpl(private val apiService: UserService): UserRemoteDataSource {
    override suspend fun updateIdentification(req: OnboardUserReqModel): Response<ApiResponse<OnboardUserResModel>> {
        return apiService.requestUpdateIdentification(req)
    }

    override suspend fun getIdentification(userId: String): Response<ApiResponse<OnboardUserResModel>> {
        return apiService.requestGetIdentification(userId)
    }

    override suspend fun getProfile(userId: String): Response<ApiResponse<ProfileResponseModel>> {
        return apiService.requestGetProfile(userId)
    }
}