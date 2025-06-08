package vn.gmi.workzen.networks

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.networks.api.AttendanceService
import vn.gmi.workzen.networks.api.AuthenticationService
import vn.gmi.workzen.networks.api.ShiftService
import vn.gmi.workzen.networks.api.UserService
import vn.gmi.workzen.networks.rest.RestClient
import vn.gmi.workzen.utils.MySharedPreferences


class ApiService private constructor() {

    private val GSON: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        .setLenient()
        .create()

    private var baseUrl: String = ""

    private var retrofit: Retrofit? = null



    companion object {
        private var INSTANCE: ApiService? = null
        val instance: ApiService by lazy { ApiService() }
    }

    fun initBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
        retrofit = RestClient.buildService(baseUrl, GSON)
    }


    val authenticationService: AuthenticationService
        get() = retrofit?.create(AuthenticationService::class.java)
            ?: throw IllegalStateException("Retrofit chưa được init. Gọi initBaseUrl() trước.")

    val userService: UserService
        get() = retrofit?.create(UserService::class.java)
            ?: throw IllegalStateException("Retrofit chưa được init. Gọi initBaseUrl() trước.")

    val shiftService: ShiftService
        get() = retrofit?.create(ShiftService::class.java)
            ?: throw IllegalStateException("Retrofit chưa được init. Gọi initBaseUrl() trước.")

    val attendanceService: AttendanceService
        get() = retrofit?.create(AttendanceService::class.java)
            ?: throw IllegalStateException("Retrofit chưa được init. Gọi initBaseUrl() trước.")
}
