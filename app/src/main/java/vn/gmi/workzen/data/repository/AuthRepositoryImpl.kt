package vn.gmi.workzen.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import vn.gmi.workzen.data.datasource.local.auth.AuthLocalDataSource
import vn.gmi.workzen.data.datasource.remote.auth.AuthRemoteDataSource
import vn.gmi.workzen.data.di.IoDispatcher
import vn.gmi.workzen.data.models.auth.LoginRequestModel
import vn.gmi.workzen.data.models.auth.AuthResponse
import vn.gmi.workzen.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource:AuthLocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AuthRepository {

    override suspend fun login(request: LoginRequestModel): AuthResponse {
        val response = remoteDataSource.login(request)
        localDataSource.storeAuth(response)
        return response
    }

    override suspend fun logout() {

    }

    override suspend fun refreshToken(token: String) {

    }
}