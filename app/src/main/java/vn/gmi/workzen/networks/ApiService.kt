package vn.gmi.workzen.networks

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import retrofit2.Retrofit
import vn.gmi.workzen.BuildConfig
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.networks.api.AttendanceService
import vn.gmi.workzen.networks.api.AuthenticationService
import vn.gmi.workzen.networks.api.ConversationService
import vn.gmi.workzen.networks.api.NotificationService
import vn.gmi.workzen.networks.api.ShiftService
import vn.gmi.workzen.networks.api.UserService
import vn.gmi.workzen.networks.rest.RestClient
import vn.gmi.workzen.services.socket.AppWebSocketListener
import vn.gmi.workzen.utils.MySharedPreferences
import java.lang.reflect.Type
import java.time.Instant


class ApiService private constructor() {

     val GSON: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        .registerTypeAdapter(Instant::class.java, object : JsonDeserializer<Instant> {
            override fun deserialize(
                json: JsonElement,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): Instant {
                return Instant.parse(json.asString)
            }
        })
        .registerTypeAdapter(Instant::class.java, object : JsonSerializer<Instant> {
            override fun serialize(
                src: Instant,
                typeOfSrc: Type?,
                context: JsonSerializationContext?
            ): JsonElement {
                return JsonPrimitive(src.toString()) // ISO-8601 format
            }
        })
        .setLenient()
        .create()


    private var baseUrl: String = ""
    private var webSocketUrl: String = BuildConfig.WEB_SOCKET_URL
    private var retrofit: Retrofit? = null
    private var webSocket: okhttp3.WebSocket? = null


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

    val notificationService: NotificationService
        get() = retrofit?.create(NotificationService::class.java)
            ?: throw IllegalStateException("Retrofit chưa được init. Gọi initBaseUrl() trước.")

    val conversationService: ConversationService
        get() = retrofit?.create(ConversationService::class.java)
            ?: throw IllegalStateException("Retrofit chưa được init. Gọi initBaseUrl() trước.")
}

