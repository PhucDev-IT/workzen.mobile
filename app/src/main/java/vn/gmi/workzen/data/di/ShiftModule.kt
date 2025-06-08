package vn.gmi.workzen.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import vn.gmi.workzen.data.datasource.local.shift.ShiftLocalDataSource
import vn.gmi.workzen.data.datasource.local.shift.ShiftLocalDataSourceImpl
import vn.gmi.workzen.data.datasource.remote.shift.ShiftRemoteDataSource
import vn.gmi.workzen.data.datasource.remote.shift.ShiftRemoteDataSourceImpl
import vn.gmi.workzen.data.repository.ShiftRepositoryImpl
import vn.gmi.workzen.domain.repository.ShiftRepository
import vn.gmi.workzen.domain.usecase.GetShiftByUserUseCase
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.api.ShiftService
import vn.gmi.workzen.ui.home.time_keeping.TimeKeepingContract
import vn.gmi.workzen.ui.home.time_keeping.TimeKeepingPresenter

@Module
@InstallIn(SingletonComponent::class)
class ShiftModule {
    @Provides
    fun provideShiftApi(): ShiftService {
        return ApiService.instance.shiftService
    }

    @Provides
    fun provideShiftRemoteDataSource(api: ShiftService): ShiftRemoteDataSource {
        return ShiftRemoteDataSourceImpl(api)
    }

    @Provides
    fun provideShiftLocalDataSource(): ShiftLocalDataSource {
        return ShiftLocalDataSourceImpl()
    }

   @Provides
   fun provideShiftRepository(
       remote: ShiftRemoteDataSource,
       local: ShiftLocalDataSource,
       @IoDispatcher dispatcher: CoroutineDispatcher
   ): ShiftRepository {
       return ShiftRepositoryImpl(remote, local,dispatcher)
   }

    @Provides
    fun provideGetShiftByUserUseCase(repo: ShiftRepository): GetShiftByUserUseCase {
        return GetShiftByUserUseCase(repo)
    }


}

