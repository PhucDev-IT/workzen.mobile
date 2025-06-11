package vn.gmi.workzen.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import vn.gmi.workzen.data.datasource.local.user.UserLocalDataSource
import vn.gmi.workzen.data.datasource.remote.user.UserRemoteDataSource
import vn.gmi.workzen.data.di.IoDispatcher
import vn.gmi.workzen.data.models.request.user.OnboardUserReqModel
import vn.gmi.workzen.data.models.response.user.ProfileResponseModel
import vn.gmi.workzen.domain.entity.user.IdentificationEntity
import vn.gmi.workzen.domain.entity.user.ProfileEntity
import vn.gmi.workzen.domain.repository.UserRepository
import vn.gmi.workzen.networks.rest.ApiResult
import vn.gmi.workzen.networks.rest.toApiResult
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : UserRepository {
    override suspend fun updateIdentification(request: OnboardUserReqModel): IdentificationEntity = withContext(dispatcher) {
        when (val result = remoteDataSource.updateIdentification(request).toApiResult()) {
            is ApiResult.Success -> {
                val entity = result.data.mapToEntity()
                localDataSource.saveIdentification(entity)
                entity
            }
            is ApiResult.Error -> throw Exception(result.message)
        }
    }

    override suspend fun getIdentification(params: String): IdentificationEntity {
        return withContext(dispatcher) {
            var entity: IdentificationEntity? = localDataSource.getIdentification()
            if (entity == null) {
                entity = when (val result = remoteDataSource.getIdentification(params).toApiResult()) {
                    is ApiResult.Success -> {
                        val mapped = result.data.mapToEntity()
                        localDataSource.saveIdentification(mapped)
                        mapped
                    }
                    is ApiResult.Error -> throw Exception(result.message)
                }
            }
            entity
        }
    }

    override suspend fun getProfile(userId: String): ProfileEntity? {
       return withContext(dispatcher) {
            var entity: ProfileEntity? = localDataSource.getProfile()
            if (entity == null) {
                entity = when (val result = remoteDataSource.getProfile(userId).toApiResult()) {
                    is ApiResult.Success -> {
                        val mapped = result.data.mapToEntity()
                        localDataSource.storeProfile(mapped)
                        mapped
                    }
                    is ApiResult.Error -> throw Exception(result.message)
                }
            }
            entity
        }
    }

    override suspend fun storeProfile(model: ProfileEntity) {

    }
}