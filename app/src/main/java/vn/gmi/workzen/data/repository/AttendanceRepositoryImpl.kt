package vn.gmi.workzen.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import vn.gmi.workzen.data.datasource.local.attendance.AttendanceLocalDataSource
import vn.gmi.workzen.data.datasource.remote.attendance.AttendanceRemoteDataSource
import vn.gmi.workzen.data.di.IoDispatcher
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.response.attendance.GetWorkScheduleResModel
import vn.gmi.workzen.domain.entity.attendance.MonthlyWorkOverviewEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity
import vn.gmi.workzen.domain.entity.attendance.StatisticSalaryOfYearEntity
import vn.gmi.workzen.domain.repository.AttendanceRepository
import vn.gmi.workzen.manager.SessionManager
import vn.gmi.workzen.networks.rest.ApiResult
import vn.gmi.workzen.networks.rest.ErrorCode
import vn.gmi.workzen.networks.rest.toApiResult
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val attendanceRemoteDataSource: AttendanceRemoteDataSource,
    private val attendanceLocalDataSource: AttendanceLocalDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AttendanceRepository{
    override suspend fun checkIn(req: CheckInRequestModel): GetWorkScheduleResModel?  = withContext(dispatcher){
        when(val result = attendanceRemoteDataSource.checkIn(req).toApiResult()){
            is ApiResult.Success -> result.data
            is ApiResult.Error -> {
                if(result.code == ErrorCode.DATA_EMPTY){
                    null
                }
                throw Exception(result.message)
            }
        }
    }

    override suspend fun checkOut(req: CheckoutReqModel): GetWorkScheduleResModel? = withContext(dispatcher) {
        when(val result = attendanceRemoteDataSource.checkOut(req).toApiResult()){
            is ApiResult.Success -> result.data
            is ApiResult.Error -> {
                if(result.code == ErrorCode.DATA_EMPTY){
                    null
                }
                throw Exception(result.message)
            }
        }

    }

    override suspend fun getWorkScheduleTodayUseCase(params: String): GetWorkScheduleResModel?  = withContext(dispatcher){
        when(val result = attendanceRemoteDataSource.getWorkScheduleToday(params).toApiResult()){
            is ApiResult.Success -> result.data
            is ApiResult.Error -> {
                if(result.code == ErrorCode.DATA_EMPTY){
                    null
                }
                throw Exception(result.message)
            }
        }
    }

    override suspend fun reportAttendanceByMonthYearLocal(
        month: Int,
        year: Int
    ): ReportWorkSheetMonthYearEntity? {
        return withContext(dispatcher){
            val entity = attendanceLocalDataSource.getWorkSheetMonthYear("${SessionManager.profileState.value?.id}_${month}_${year}")
            entity
        }
    }

    override suspend fun reportAttendanceByMonthYearRemote(
        month: Int,
        year: Int
    ): ReportWorkSheetMonthYearEntity? {
        return withContext(dispatcher) {
           val entity = when (val result = attendanceRemoteDataSource.getReportWorkSheetInMonthYear(month,year).toApiResult()) {
                is ApiResult.Success -> {
                    val mapped = result.data?.mapToEntity()
                    mapped?.id = "${SessionManager.profileState.value?.id}_${month}_${year}"
                    if(mapped != null){
                        attendanceLocalDataSource.saveWorkSheetMonthYear(mapped)
                    }
                    mapped
                }
                is ApiResult.Error -> throw Exception(result.message)
            }
            entity
        }
    }

    override suspend fun getReportSalaryOfYearRemote(year: Int): StatisticSalaryOfYearEntity? {
      return withContext(dispatcher){
          val entity = when(val result = attendanceRemoteDataSource.getReportSalaryOfYear(year).toApiResult()){
            is ApiResult.Success -> {
                val mapped = result.data?.mapToEntity()
                mapped
            }
              is ApiResult.Error -> throw Exception(result.message)
          }
          entity
      }
    }

    override suspend fun getReportSalaryOfYearLocal(year: Int): StatisticSalaryOfYearEntity? {
        return withContext(dispatcher){
            val entity = attendanceLocalDataSource.getReportSalaryOfYear(year)
            entity
        }
    }


    override suspend fun getReportWorkSheetTheDayLocal(id: String): ReportWorkSheetDayEntity? {
        return withContext(dispatcher){
            val entity = attendanceLocalDataSource.getReportWorkSheetTheDay(id)
            entity
        }
    }

    override suspend fun getMonthlyWorkOverviewRemote(
        month: Int,
        year: Int
    ): MonthlyWorkOverviewEntity? {
      return  withContext(dispatcher){
          val entity = when(val result = attendanceRemoteDataSource.getMonthlyWorkOverview(month,year).toApiResult()){
              is ApiResult.Success -> {
                  val mapped = result.data?.mapToEntity()
                  mapped
              }
              is ApiResult.Error -> throw Exception(result.message)
          }
          entity
      }
    }

    override suspend fun getMonthlyWorkOverviewLocal(
        month: Int,
        year: Int
    ): MonthlyWorkOverviewEntity? {
         return  withContext(dispatcher){
             val entity = attendanceLocalDataSource.getReportMonthlyWorkOverview(month, year, SessionManager.profileState.value?.id!!)
             entity
         }
    }
}