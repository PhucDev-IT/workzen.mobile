package vn.gmi.workzen.networks.rest

import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.utils.MySharedPreferences
import java.util.concurrent.TimeUnit

class RestClient {

    companion object{
        fun buildService(baseUrl:String, gson: Gson): Retrofit {
            val bearToken =  MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_BEAR_ACCESS_TOKEN) ?: ""
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(okHttpClient(bearToken).build())  // Pass token to okHttpClient
                .build()
        }

        private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        // Modified okHttpClient to take token as parameter
        private fun okHttpClient(token: String?): OkHttpClient.Builder {
            val builder = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)

            // Chỉ thêm interceptor nếu token không rỗng
            if (!token.isNullOrBlank()) {
                builder.addInterceptor(interceptor(token))
            }

            return builder
        }


        private fun interceptor(token: String?): Interceptor {
            return Interceptor { chain ->
                val originalRequest = chain.request()
                val modifiedRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()

                // Custom log headers
                Log.d("RestClient", "=== REQUEST HEADERS ===")
                modifiedRequest.headers.forEach {
                    Log.d("RestClient", "${it.first}: ${it.second}")
                }

                val response = chain.proceed(modifiedRequest)

                // Custom log response headers
                Log.d("RestClient", "=== RESPONSE HEADERS ===")
                response.headers.forEach {
                    Log.d("RestClient", "${it.first}: ${it.second}")
                }

                response
            }
        }

    }
}