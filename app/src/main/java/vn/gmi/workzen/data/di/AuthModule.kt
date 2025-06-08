package vn.gmi.workzen.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import vn.gmi.workzen.data.datasource.local.auth.AuthLocalDataSource
import vn.gmi.workzen.data.datasource.local.auth.AuthLocalDataSourceImpl
import vn.gmi.workzen.data.datasource.remote.auth.AuthRemoteDataSource
import vn.gmi.workzen.data.datasource.remote.auth.AuthRemoteDataSourceImpl
import vn.gmi.workzen.data.repository.AuthRepositoryImpl
import vn.gmi.workzen.domain.repository.AuthRepository
import vn.gmi.workzen.domain.usecase.LoginUseCase
import vn.gmi.workzen.domain.usecase.LogoutUseCase
import vn.gmi.workzen.domain.usecase.PhoneExistsUseCase
import vn.gmi.workzen.domain.usecase.RegisterUseCase
import vn.gmi.workzen.domain.usecase.VerifyTokenUseCase
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.api.AuthenticationService
import vn.gmi.workzen.ui.authentication.login.LoginContract
import vn.gmi.workzen.ui.authentication.login.LoginPresenter
import vn.gmi.workzen.ui.authentication.signup.RegisterContract
import vn.gmi.workzen.ui.authentication.signup.RegisterPresenter
import vn.gmi.workzen.ui.splash.SplashContract
import vn.gmi.workzen.ui.splash.SplashPresenter


@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
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

    @Provides
    fun providerPhoneExistsUseCase(repo: AuthRepository): PhoneExistsUseCase {
        return PhoneExistsUseCase(repo)
    }

    @Provides
    fun providerRegisterUseCase(repo: AuthRepository): RegisterUseCase {
        return RegisterUseCase(repo)
    }

    @Provides
    fun provideVerifyTokenUseCase(repo: AuthRepository): VerifyTokenUseCase {
        return VerifyTokenUseCase(repo)
    }

    @Provides
    fun provideSplashPresenter(
        verifyTokenUseCase: VerifyTokenUseCase
    ): SplashContract.Presenter {
        return SplashPresenter(verifyTokenUseCase)
    }

    @Provides
    fun provideLoginPresenter(
        loginUseCase: LoginUseCase
    ): LoginContract.Presenter {
        return LoginPresenter(loginUseCase)
    }

    @Provides
    fun providerRegisterPresenter(registerUseCase: RegisterUseCase, phoneExistsUseCase: PhoneExistsUseCase): RegisterContract.Presenter {
        return RegisterPresenter(registerUseCase,phoneExistsUseCase)
    }
}