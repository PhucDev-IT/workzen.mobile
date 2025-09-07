package vn.gmi.workzen.networks.rest

import retrofit2.Response
import com.google.gson.Gson
import vn.gmi.workzen.networks.models.ApiResponse

enum class ErrorCode{
    RESPONSE_ERROR,
    DATA_EMPTY,
    UNKNOWN_ERROR
}

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val code: ErrorCode,val message: String) : ApiResult<Nothing>()
}

inline fun <T> ApiResult<T>.onSuccess(action: (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) action(data)
    return this
}

inline fun <T> ApiResult<T>.onError(action: (String) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) action(message)
    return this
}

suspend fun <T> networkCallback(
    apiCall: suspend () -> Response<ApiResponse<T>>
): ApiResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            when {
                body == null -> ApiResult.Error(ErrorCode.RESPONSE_ERROR,"Empty response body.")
                !body.success -> ApiResult.Error(ErrorCode.RESPONSE_ERROR,body.message ?: "Unknown API error")
                body.data == null -> ApiResult.Error(ErrorCode.DATA_EMPTY,"Empty data.")
                else -> ApiResult.Success(body.data)
            }
        } else {
            val errorMsg = response.errorBody()?.string()?.let {
                try {
                    val json = Gson().fromJson(it, Map::class.java)
                    json["message"]?.toString() ?: "Unknown server error"
                } catch (e: Exception) {
                    it
                }
            } ?: "HTTP ${response.code()}"
            ApiResult.Error(ErrorCode.RESPONSE_ERROR,errorMsg)
        }
    } catch (e: Exception) {
        ApiResult.Error(ErrorCode.UNKNOWN_ERROR,e.localizedMessage ?: "Unexpected error")
    }
}


suspend fun <T> Response<ApiResponse<T>>.toApiResult(): ApiResult<T> {
    return try {
        if (isSuccessful) {
            val body = body()
            when {
                body == null -> ApiResult.Error(ErrorCode.RESPONSE_ERROR,"Empty response body.")
                !body.success -> ApiResult.Error(ErrorCode.RESPONSE_ERROR,body.message ?: "Unknown API error")
                body.data == null -> ApiResult.Error(ErrorCode.DATA_EMPTY,"Empty data.")
                else -> ApiResult.Success(body.data)
            }
        } else {
            val errorMsg = errorBody()?.string()?.let {
                try {
                    val json = Gson().fromJson(it, Map::class.java)
                    json["message"]?.toString() ?: "Unknown server error"
                } catch (e: Exception) {
                    it
                }
            } ?: "HTTP ${code()}"
            ApiResult.Error(ErrorCode.RESPONSE_ERROR,errorMsg)
        }
    } catch (e: Exception) {
        ApiResult.Error(ErrorCode.UNKNOWN_ERROR,e.localizedMessage ?: "Unexpected error")
    }
}

