package vn.gmi.workzen.networks

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.networks.api.AuthenticationService
import vn.gmi.workzen.networks.rest.RestClient
import vn.gmi.workzen.utils.MySharedPreferences


class ApiService private constructor() {

    private val GSON: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        .setLenient()
        .create()

    private var baseUrl: String = ""
    private var token: String
    private var retrofit: Retrofit? = null

    init {

        token = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_BEAR_ACCESS_TOKEN) ?: ""
    }

    companion object {
        private var INSTANCE: ApiService? = null
        val instance: ApiService by lazy { ApiService() }
    }

    fun initBaseUrl(baseUrl: String) {
        this.baseUrl = baseUrl
        retrofit = RestClient.buildService(baseUrl, GSON, this.token)
    }

    val authenticationService: AuthenticationService
        get() = retrofit?.create(AuthenticationService::class.java)
            ?: throw IllegalStateException("Retrofit chưa được init. Gọi initBaseUrl() trước.")
}
