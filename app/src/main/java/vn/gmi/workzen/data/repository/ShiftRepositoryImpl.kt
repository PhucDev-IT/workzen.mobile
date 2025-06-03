package vn.gmi.workzen.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import vn.gmi.workzen.data.datasource.local.shift.ShiftLocalDataSource
import vn.gmi.workzen.data.datasource.remote.shift.ShiftRemoteDataSource
import vn.gmi.workzen.data.di.IoDispatcher
import vn.gmi.workzen.data.models.response.shift.ShiftResponseModel
import vn.gmi.workzen.domain.entity.ShiftEntity
import vn.gmi.workzen.domain.repository.ShiftRepository
import vn.gmi.workzen.networks.rest.ApiResult
import vn.gmi.workzen.networks.rest.toApiResult

class ShiftRepositoryImpl(
    private val remoteDataSource: ShiftRemoteDataSource,
    private val localDataSource: ShiftLocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): ShiftRepository {
    override suspend fun getShiftByUser(userId: String): ShiftEntity? = withContext(dispatcher){
        when (val result = remoteDataSource.getShiftByUser(userId).toApiResult()) {
            is ApiResult.Success -> {
                val entity = result.data.mapToEntity()
                localDataSource.saveShift(entity)
                entity
            }
            is ApiResult.Error -> throw Exception(result.message)
        }
    }
}