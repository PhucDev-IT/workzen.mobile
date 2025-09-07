package vn.gmi.workzen.data.datasource.remote.shift

import retrofit2.Response
import vn.gmi.workzen.data.models.response.shift.ShiftResponseModel
import vn.gmi.workzen.networks.api.AuthenticationService
import vn.gmi.workzen.networks.api.ShiftService
import vn.gmi.workzen.networks.models.ApiResponse

class ShiftRemoteDataSourceImpl (private val apiService: ShiftService): ShiftRemoteDataSource  {
    override suspend fun getShiftByUser(userId: String): Response<ApiResponse<ShiftResponseModel>> {
        return apiService.getShiftByUser(userId)
    }
}