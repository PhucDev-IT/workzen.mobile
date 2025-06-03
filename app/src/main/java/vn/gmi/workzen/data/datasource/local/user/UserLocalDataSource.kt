package vn.gmi.workzen.data.datasource.local.user

import vn.gmi.workzen.data.models.response.user.UserResponseModel
import vn.gmi.workzen.domain.entity.IdentificationEntity
import vn.gmi.workzen.domain.entity.ProfileEntity

interface UserLocalDataSource {
    suspend fun saveIdentification(model: IdentificationEntity)
    suspend fun getIdentification(): IdentificationEntity?
    suspend fun getProfile(): ProfileEntity?
    suspend fun storeProfile(model: ProfileEntity)
}