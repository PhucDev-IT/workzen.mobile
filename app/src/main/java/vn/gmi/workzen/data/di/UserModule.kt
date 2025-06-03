package vn.gmi.workzen.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import vn.gmi.workzen.data.datasource.local.user.UserLocalDataSource
import vn.gmi.workzen.data.datasource.local.user.UserLocalDataSourceImpl
import vn.gmi.workzen.data.datasource.remote.user.UserRemoteDataSource
import vn.gmi.workzen.data.datasource.remote.user.UserRemoteDataSourceImpl
import vn.gmi.workzen.data.repository.UserRepositoryImpl
import vn.gmi.workzen.domain.repository.UserRepository
import vn.gmi.workzen.domain.usecase.GetIdentificationUseCase
import vn.gmi.workzen.domain.usecase.GetProfileUseCase
import vn.gmi.workzen.domain.usecase.LoginUseCase
import vn.gmi.workzen.domain.usecase.PhoneExistsUseCase
import vn.gmi.workzen.domain.usecase.RegisterUseCase
import vn.gmi.workzen.domain.usecase.UpdateIdentificationUseCase
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.api.UserService
import vn.gmi.workzen.ui.authentication.login.LoginContract
import vn.gmi.workzen.ui.authentication.login.LoginPresenter
import vn.gmi.workzen.ui.authentication.signup.RegisterContract
import vn.gmi.workzen.ui.authentication.signup.RegisterPresenter
import vn.gmi.workzen.ui.home.HomeContract
import vn.gmi.workzen.ui.home.HomePresenter
import vn.gmi.workzen.ui.profile.ProfileContract
import vn.gmi.workzen.ui.profile.ProfilePresenter

@Module
@InstallIn(SingletonComponent::class)
class UserModule {
    @Provides
    fun provideUserApi(): UserService {
        return ApiService.instance.userService
    }

    @Provides
    fun provideRemoteDataSource(api: UserService): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(api)
    }

    @Provides
    fun providerLocalDataSource(): UserLocalDataSource {
        return UserLocalDataSourceImpl()
    }

    @Provides
    fun providerUserRepository(
        remote: UserRemoteDataSource,
        local: UserLocalDataSource,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): UserRepository {
        return UserRepositoryImpl(remote, local,dispatcher)
    }

    @Provides
    fun providerUpdateIdentificationUseCase(repo: UserRepository): UpdateIdentificationUseCase {
        return UpdateIdentificationUseCase(repo)
    }

    @Provides
    fun providerGetIdentificationUseCase(repo: UserRepository): GetIdentificationUseCase{
        return GetIdentificationUseCase(repo)
    }

    @Provides
    fun provideGetProfileUseCase(repo: UserRepository): GetProfileUseCase{
        return GetProfileUseCase(repo)
    }

    @Provides
    fun provideHomePresenter(
        getIdentificationUseCase: GetIdentificationUseCase
    ): HomeContract.Presenter {
        return HomePresenter(getIdentificationUseCase)
    }

    @Provides
    fun provideProfilePresenter(
        getProfileUseCase: GetProfileUseCase
    ): ProfileContract.Presenter{
        return ProfilePresenter(getProfileUseCase)
    }

}