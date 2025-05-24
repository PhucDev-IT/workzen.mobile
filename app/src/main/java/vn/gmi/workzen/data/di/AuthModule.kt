package vn.gmi.workzen.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import vn.gmi.workzen.data.apis.AuthenticationService
import vn.gmi.workzen.data.datasource.local.auth.AuthLocalDataSource
import vn.gmi.workzen.data.datasource.local.auth.AuthLocalDataSourceImpl
import vn.gmi.workzen.data.datasource.remote.auth.AuthRemoteDataSource
import vn.gmi.workzen.data.datasource.remote.auth.AuthRemoteDataSourceImpl
import vn.gmi.workzen.data.repository.AuthRepositoryImpl
import vn.gmi.workzen.domain.repository.AuthRepository
import vn.gmi.workzen.domain.usecase.LoginUseCase
import vn.gmi.workzen.domain.usecase.LogoutUseCase
import vn.gmi.workzen.networks.ApiService

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideAuthApi(): AuthenticationService {
        return ApiService.instance.authenticationService
    }

    @Provides
    fun provideRemoteDataSource(api: AuthenticationService): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(api)
    }

    @Provides
    fun provideLocalDataSource(): AuthLocalDataSource {
        return AuthLocalDataSourceImpl()
    }

    @Provides
    fun provideAuthRepository(
        remote: AuthRemoteDataSource,
        local: AuthLocalDataSource,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): AuthRepository {
        return AuthRepositoryImpl(remote, local, dispatcher)
    }

    @Provides
    fun provideLoginUseCase(repo: AuthRepository): LoginUseCase {
        return LoginUseCase(repo)
    }

    @Provides
    fun provideLogoutUseCase(repo: AuthRepository): LogoutUseCase {
        return LogoutUseCase(repo)
    }
}