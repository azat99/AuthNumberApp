package com.example.app1.model

import androidx.annotation.Nullable
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

const val BASE_URL = "https://api-test.samalsauda.kz/api/v1/auth/client/"

interface ApiService {

    @POST("check_account")
    suspend fun checkNumber(@Query("phone") phone: String): Response<RequestBody>

    @POST("get_code")
    suspend fun getCode(@Query("phone" ) phone: String): Response<RequestBody>

    companion object {
        private val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

        private val okHttp = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        operator fun invoke(): ApiService {
            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttp)
                    .build()
                    .create(ApiService::class.java)
        }

    }
}