package vn.gmi.workzen.networks.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vn.gmi.workzen.data.models.response.shift.ShiftResponseModel
import vn.gmi.workzen.networks.models.ApiResponse

interface ShiftService {
    @GET(EndPoints.GET_SHIFT_BY_USER)
    suspend fun getShiftByUser(@Path("id") id: String): Response<ApiResponse<ShiftResponseModel>>
}