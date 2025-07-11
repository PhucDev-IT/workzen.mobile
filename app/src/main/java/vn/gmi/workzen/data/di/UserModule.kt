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
import vn.gmi.workzen.domain.usecase.GetProfileLocalUseCase
import vn.gmi.workzen.domain.usecase.GetProfileRemoteUseCase
import vn.gmi.workzen.domain.usecase.GetProfileUseCase
import vn.gmi.workzen.domain.usecase.LogoutUseCase
import vn.gmi.workzen.domain.usecase.StoreProfileUseCase
import vn.gmi.workzen.domain.usecase.UpdateIdentificationUseCase
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.api.UserService
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
   fun getProfileRemoteUseCase(repo: UserRepository): GetProfileRemoteUseCase{
       return GetProfileRemoteUseCase(repo)
   }

    @Provides
    fun providerGetProfileLocalUseCase(repo: UserRepository): GetProfileLocalUseCase{
        return GetProfileLocalUseCase(repo)
    }

    @Provides
    fun provideHomePresenter(
        getProfileRemoteUseCase: GetProfileRemoteUseCase,
        getProfileLocalUseCase: GetProfileLocalUseCase,
        storeProfileUseCase: StoreProfileUseCase
    ): HomeContract.Presenter {
        return HomePresenter(getProfileRemoteUseCase, getProfileLocalUseCase,storeProfileUseCase)
    }

    @Provides
    fun providerGetProfile(repo: UserRepository): GetProfileUseCase{
        return GetProfileUseCase(repo)
    }

    @Provides
    fun provideStoreProfile(repo: UserRepository): StoreProfileUseCase{
        return StoreProfileUseCase(repo)
    }

    @Provides
    fun provideProfilePresenter(
        getProfileUseCase: GetProfileUseCase,
        logoutUseCase: LogoutUseCase
    ): ProfileContract.Presenter{
        return ProfilePresenter(getProfileUseCase,logoutUseCase)
    }

}