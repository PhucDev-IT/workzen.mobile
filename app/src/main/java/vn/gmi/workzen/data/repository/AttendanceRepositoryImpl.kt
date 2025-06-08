package vn.gmi.workzen.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import vn.gmi.workzen.data.datasource.remote.attendance.AttendanceRemoteDataSource
import vn.gmi.workzen.data.di.IoDispatcher
import vn.gmi.workzen.data.models.request.attendance.CheckInRequestModel
import vn.gmi.workzen.data.models.request.attendance.CheckoutReqModel
import vn.gmi.workzen.data.models.request.attendance.InfoAttendanceParams
import vn.gmi.workzen.data.models.response.attendance.AttendanceResModel
import vn.gmi.workzen.domain.repository.AttendanceRepository
import vn.gmi.workzen.networks.rest.ApiResult
import vn.gmi.workzen.networks.rest.ErrorCode
import vn.gmi.workzen.networks.rest.toApiResult
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val attendanceRemoteDataSource: AttendanceRemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AttendanceRepository{
    override suspend fun checkIn(req: CheckInRequestModel): AttendanceResModel?  = withContext(dispatcher){
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

    override suspend fun checkOut(req: CheckoutReqModel): AttendanceResModel? = withContext(dispatcher) {
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

    override suspend fun getInfo(params: InfoAttendanceParams): AttendanceResModel? = withContext(dispatcher) {
        when(val result = attendanceRemoteDataSource.getInfo(params.accountId,params.date).toApiResult()){
            is ApiResult.Success -> result.data
            is ApiResult.Error -> {
                if(result.code == ErrorCode.DATA_EMPTY){
                    null
                }
                throw Exception(result.message)
            }
        }
    }
}