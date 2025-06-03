package vn.gmi.workzen.data.datasource.remote.shift

import retrofit2.Response
import vn.gmi.workzen.data.models.response.shift.ShiftResponseModel
import vn.gmi.workzen.networks.models.ApiResponse

interface ShiftRemoteDataSource {
   suspend fun getShiftByUser(userId: String): Response<ApiResponse<ShiftResponseModel>>
}