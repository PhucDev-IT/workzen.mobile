package vn.gmi.workzen.domain.repository

import vn.gmi.workzen.data.models.request.auth.LoginRequestModel
import vn.gmi.workzen.data.models.request.user.OnboardUserReqModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.data.models.response.user.ProfileResponseModel
import vn.gmi.workzen.domain.entity.user.IdentificationEntity
import vn.gmi.workzen.domain.entity.user.ProfileEntity

interface UserRepository {
    suspend fun updateIdentification(request: OnboardUserReqModel): IdentificationEntity
    suspend fun getProfile(userId: String): ProfileEntity?
    suspend fun storeProfile(model: ProfileEntity)

}