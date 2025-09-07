package vn.gmi.workzen.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import vn.gmi.workzen.data.datasource.local.notification.NotificationLocalDataSource
import vn.gmi.workzen.data.datasource.local.notification.NotificationLocalDataSourceImpl
import vn.gmi.workzen.data.datasource.remote.notification.NotificationRemoteDataSource
import vn.gmi.workzen.data.datasource.remote.notification.NotificationRemoteDataSourceImpl
import vn.gmi.workzen.data.repository.NotificationRepositoryImpl
import vn.gmi.workzen.domain.repository.NotificationRepository
import vn.gmi.workzen.domain.usecase.CountNotificationUseCase
import vn.gmi.workzen.domain.usecase.GetNotificationLocalUseCase
import vn.gmi.workzen.domain.usecase.GetNotificationRemoteUseCase
import vn.gmi.workzen.domain.usecase.MarkAsReadNotificationUseCase
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.api.NotificationService
import vn.gmi.workzen.ui.home.header.HomeHeaderContract
import vn.gmi.workzen.ui.home.header.HomeHeaderPresenter
import vn.gmi.workzen.ui.notification.NotificationContract
import vn.gmi.workzen.ui.notification.NotificationPresenter

@Module
@InstallIn(SingletonComponent::class)
class NotificationModule {
    @Provides
    fun provideNotificationApi(): NotificationService{
        return ApiService.instance.notificationService
    }

    //Use case
    @Provides
    fun provideGetNotificationRemoteUseCase(repo: NotificationRepository): GetNotificationRemoteUseCase {
        return GetNotificationRemoteUseCase(repo)
    }

    @Provides
    fun provideGetNotificationLocalUseCase(repo: NotificationRepository): GetNotificationLocalUseCase {
        return GetNotificationLocalUseCase(repo)
    }

    @Provides
    fun provideCountNotificationUseCase(repo: NotificationRepository): CountNotificationUseCase {
        return CountNotificationUseCase(repo)
    }

    @Provides
    fun provideMarkAsReadNotificationUseCase(repo: NotificationRepository): MarkAsReadNotificationUseCase {
        return MarkAsReadNotificationUseCase(repo)
    }

    //Data source
    @Provides
    fun provideNotificationLocalDataSource(): NotificationLocalDataSource {
        return NotificationLocalDataSourceImpl()
    }

    @Provides
    fun provideNotificationRemoteDataSource(service: NotificationService): NotificationRemoteDataSource{
        return NotificationRemoteDataSourceImpl(service)
    }

    @Provides
    fun provideNotificationRepository(
        remote: NotificationRemoteDataSource,
        local: NotificationLocalDataSource,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): NotificationRepository{
        return NotificationRepositoryImpl(remote, local,dispatcher)
    }

    @Provides
    fun provideNotificationPresenter(
        getNotificationRemoteUseCase: GetNotificationRemoteUseCase,
        getNotificationLocalUseCase: GetNotificationLocalUseCase,
        markAsReadNotificationUseCase: MarkAsReadNotificationUseCase
    ):NotificationContract.Presenter{
        return NotificationPresenter(getNotificationRemoteUseCase,getNotificationLocalUseCase,markAsReadNotificationUseCase)
    }

    @Provides
    fun provideHomeHeaderPresenter(
        countNotificationUseCase: CountNotificationUseCase
    ):HomeHeaderContract.Presenter{
        return HomeHeaderPresenter(countNotificationUseCase)

    }
}