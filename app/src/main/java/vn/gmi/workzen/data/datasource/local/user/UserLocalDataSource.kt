package vn.gmi.workzen.data.datasource.local.user

import kotlinx.coroutines.flow.Flow
import vn.gmi.workzen.data.models.response.user.UserResponseModel
import vn.gmi.workzen.domain.entity.user.IdentificationEntity
import vn.gmi.workzen.domain.entity.user.ProfileEntity

interface UserLocalDataSource {
    suspend fun saveIdentification(model: IdentificationEntity)
    suspend fun getIdentification():Flow<IdentificationEntity?>
    suspend fun getProfile(): Flow<ProfileEntity?>
    suspend fun storeProfile(model: ProfileEntity)
}