package vn.gmi.workzen.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import vn.gmi.workzen.data.datasource.local.wallet.WalletLocalDataSource
import vn.gmi.workzen.data.datasource.local.wallet.WalletLocalDataSourceImpl
import vn.gmi.workzen.data.datasource.remote.wallet.WalletRemoteDataSource
import vn.gmi.workzen.data.datasource.remote.wallet.WalletRemoteDataSourceImpl
import vn.gmi.workzen.data.repository.WalletRepositoryImpl
import vn.gmi.workzen.domain.repository.WalletRepository
import vn.gmi.workzen.domain.usecase.CreateTransactionUseCase
import vn.gmi.workzen.domain.usecase.GetLinkedWalletsUseCase
import vn.gmi.workzen.domain.usecase.GetTransactionHistoriesUseCase
import vn.gmi.workzen.domain.usecase.GetWalletIdByPhoneUseCase
import vn.gmi.workzen.domain.usecase.GetWalletsUseCase
import vn.gmi.workzen.domain.usecase.RequestLinkWalletUseCase
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.api.WalletService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WalletModule {

    //================================= SERVICE =================================
    @Provides
    fun provideWalletService(): WalletService {
        return ApiService.instance.walletService
    }


    @Provides
    fun provideWalletRemoteDataSource(service: WalletService): WalletRemoteDataSource {
        return WalletRemoteDataSourceImpl(service)
    }

    @Provides
    fun provideWalletLocalDataSource(): WalletLocalDataSource {
        return WalletLocalDataSourceImpl()
    }
    //============================== USE CASE ==================================
    @Provides
    fun provideGetWalletsUseCase(repository: WalletRepository): GetWalletsUseCase {
        return GetWalletsUseCase(repository)

    }

    @Provides
    fun provideRequestLinkWalletUseCase(repository: WalletRepository): RequestLinkWalletUseCase {
        return RequestLinkWalletUseCase(repository)
    }

    @Provides
    fun provideGetLinkedWalletsUseCase(repository: WalletRepository): GetLinkedWalletsUseCase {
        return GetLinkedWalletsUseCase(repository)
    }

    @Provides
    fun provideGetTransactionHistoriesUseCase(repository: WalletRepository): GetTransactionHistoriesUseCase {
        return GetTransactionHistoriesUseCase(repository)
    }

    @Provides
    fun provideCreateTransactionUseCase(repository: WalletRepository): CreateTransactionUseCase {
        return CreateTransactionUseCase(repository)
    }


    @Provides
    fun provideGetWalletIdByPhoneUseCase(repository: WalletRepository): GetWalletIdByPhoneUseCase {
        return GetWalletIdByPhoneUseCase(repository)
    }
    //================================ REPOSITORY ===================================

    @Provides
    fun provideWalletRepository(
        localDataSource: WalletLocalDataSource,
        remoteDataSource: WalletRemoteDataSource,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): WalletRepository {
        return WalletRepositoryImpl(localDataSource,remoteDataSource,dispatcher)
    }


    //=============================


}