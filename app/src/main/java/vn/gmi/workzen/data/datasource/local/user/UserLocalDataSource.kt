package vn.gmi.workzen.data.datasource.local.user

import vn.gmi.workzen.domain.entity.IdentificationEntity

interface UserLocalDataSource {
    suspend fun saveIdentification(model: IdentificationEntity)
    suspend fun getIdentification(userId: String): IdentificationEntity?
}