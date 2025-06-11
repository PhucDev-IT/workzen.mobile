package vn.gmi.workzen.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import vn.gmi.workzen.data.datasource.local.attendance.AttendanceLocalDataSource
import vn.gmi.workzen.data.datasource.local.attendance.AttendanceLocalDataSourceImpl
import vn.gmi.workzen.data.datasource.remote.attendance.AttendanceRemoteDataSource
import vn.gmi.workzen.data.datasource.remote.attendance.AttendanceRemoteDataSourceImpl
import vn.gmi.workzen.data.repository.AttendanceRepositoryImpl
import vn.gmi.workzen.domain.repository.AttendanceRepository
import vn.gmi.workzen.domain.usecase.CheckInUseCase
import vn.gmi.workzen.domain.usecase.CheckOutUseCase
import vn.gmi.workzen.domain.usecase.GetWorkScheduleTodayUseCase
import vn.gmi.workzen.domain.usecase.ReportAttendanceByMonthYearLocalUseCase
import vn.gmi.workzen.domain.usecase.ReportAttendanceByMonthYearRemoteUseCase
import vn.gmi.workzen.networks.ApiService
import vn.gmi.workzen.networks.api.AttendanceService
import vn.gmi.workzen.ui.home.time_keeping.TimeKeepingContract
import vn.gmi.workzen.ui.home.time_keeping.TimeKeepingPresenter
import vn.gmi.workzen.ui.worksheet.WorkSheetContract
import vn.gmi.workzen.ui.worksheet.WorkSheetPresenter

@Module
@InstallIn(SingletonComponent::class)
class AttendanceModule {

    @Provides
    fun provideAttendanceApi(): AttendanceService {
        return ApiService.instance.attendanceService
    }

    @Provides
    fun provideGetWorkScheduleTodayUseCase(attendanceRepository: AttendanceRepository): GetWorkScheduleTodayUseCase {
        return GetWorkScheduleTodayUseCase(attendanceRepository)
    }

    @Provides
    fun provideAttendanceLocalDataSource(): AttendanceLocalDataSource {
        return AttendanceLocalDataSourceImpl()
    }


    @Provides
    fun provideCheckInUseCase(attendanceRepository: AttendanceRepository): CheckInUseCase {
        return CheckInUseCase(attendanceRepository)
    }

    @Provides
    fun provideCheckOutUseCase(attendanceRepository: AttendanceRepository): CheckOutUseCase {
        return CheckOutUseCase(attendanceRepository)
    }

    @Provides
    fun provideAttendanceRemoteDataSource(attendanceService: AttendanceService): AttendanceRemoteDataSource {
        return AttendanceRemoteDataSourceImpl(attendanceService)
    }

    @Provides
    fun provideReportAttendanceByMonthYearLocalUseCase(attendanceRepository: AttendanceRepository): ReportAttendanceByMonthYearLocalUseCase {
        return ReportAttendanceByMonthYearLocalUseCase(attendanceRepository)
    }

    @Provides
    fun provideReportAttendanceByMonthYearRemoteUseCase(attendanceRepository: AttendanceRepository): ReportAttendanceByMonthYearRemoteUseCase {
        return ReportAttendanceByMonthYearRemoteUseCase(attendanceRepository)
    }

    @Provides
    fun provideWorkSheetPresenter(
        reportAttendanceByMonthYearLocalUseCase: ReportAttendanceByMonthYearLocalUseCase,
        reportAttendanceByMonthYearRemoteUseCase: ReportAttendanceByMonthYearRemoteUseCase
    ): WorkSheetContract.Presenter {
        return WorkSheetPresenter(reportAttendanceByMonthYearLocalUseCase, reportAttendanceByMonthYearRemoteUseCase)
    }



    @Provides
    fun provideAttendanceRepository(
        attendanceRemoteDataSource: AttendanceRemoteDataSource,
        attendanceLocalDataSource: AttendanceLocalDataSource,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): AttendanceRepository {
        return AttendanceRepositoryImpl(attendanceRemoteDataSource,attendanceLocalDataSource,dispatcher)
    }

    @Provides
    fun provideTimeKeepingPresenter(
        getWorkScheduleTodayUseCase: GetWorkScheduleTodayUseCase,
        checkInUseCase: CheckInUseCase,
        checkOutUseCase: CheckOutUseCase
    ): TimeKeepingContract.Presenter {
        return TimeKeepingPresenter(getWorkScheduleTodayUseCase, checkInUseCase, checkOutUseCase)
    }


}