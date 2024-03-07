package com.tatry.yandextest.data.network

import com.tatry.yandextest.data.network.dto.UserInfoDto
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.request.DeviceActionsModel
import com.tatry.yandextest.domain.model.user.UserInfoAnswerSuccess
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://api.iot.yandex.net/v1.0/"
interface YandexApi{
    @GET("user/info")
    suspend fun getUserInfo(@Header("Authorization") token: String): UserInfoAnswerSuccess

    @POST("devices/actions")
    suspend fun postDevicesActions(@Header("Authorization") token: String,
                           @Body actions: DeviceActionsModel
    ): DeviceActionsAnswerModel
}

object RetrofitInstance{

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor())
        .build()

//    private val moshi = Moshi.Builder()
//        .add(KotlinJsonAdapterFactory())
//        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
//        .addConverterFactory(MoshiConverterFactory.create())
//        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()
    val yandexApi: YandexApi = retrofit.create(YandexApi::class.java)
}