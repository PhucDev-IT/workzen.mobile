package vn.gmi.workzen.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import vn.gmi.workzen.data.datasource.local.auth.AuthLocalDataSource
import vn.gmi.workzen.data.datasource.remote.auth.AuthRemoteDataSource
import vn.gmi.workzen.data.di.IoDispatcher
import vn.gmi.workzen.data.models.request.auth.LoginRequestModel
import vn.gmi.workzen.data.models.response.auth.LoginResponseModel
import vn.gmi.workzen.data.models.response.auth.TokenResponse
import vn.gmi.workzen.domain.repository.AuthRepository
import vn.gmi.workzen.networks.rest.ApiResult
import vn.gmi.workzen.networks.rest.toApiResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AuthRepository {
    override suspend fun login(request: LoginRequestModel): LoginResponseModel = withContext(dispatcher) {
        when (val result = remoteDataSource.login(request).toApiResult()) {
            is ApiResult.Success -> {
                localDataSource.storeAuth(result.data)
                result.data
            }
            is ApiResult.Error -> throw Exception(result.message)
        }
    }



    override suspend fun checkExistsPhone(phone: String): Boolean = withContext(dispatcher) {
      when(val result = remoteDataSource.checkExistsPhone(phone).toApiResult()){
          is ApiResult.Success -> result.data
          is ApiResult.Error -> throw Exception(result.message)
      }
    }

    override suspend fun register(request: Map<String, String>): Boolean = withContext(dispatcher){
       when(val result = remoteDataSource.register(request).toApiResult()){
           is ApiResult.Success -> {
               result.data
           }
           is ApiResult.Error -> throw Exception(result.message)
       }
    }

    override suspend fun logout() {
        localDataSource.logout()
    }

    override suspend fun refreshToken(token: String): TokenResponse = withContext(dispatcher){
      when(val result = remoteDataSource.refreshToken(token).toApiResult()){
          is ApiResult.Success -> result.data
          is ApiResult.Error -> throw Exception(result.message)
      }
    }
}