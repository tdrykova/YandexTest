package com.tatry.yandextest.data.network

import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.request.DeviceActionsModel
import com.tatry.yandextest.domain.model.user.UserInfoResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

const val BASE_URL = "https://api.iot.yandex.net/v1.0/"
interface YandexApi{
    @GET("user/info")
    suspend fun getUserInfo(@Header("Authorization") token: String): UserInfoResponse

    @GET("devices/{device_id}")
    suspend fun getDeviceState(@Header("Authorization") token: String,
                               @Path("device_id") deviceId: String): GetDeviceStateResponse

    @Headers("Content-Type: application/json")
    @POST("devices/actions")
    suspend fun controlDeviceActions(@Header("Authorization") token: String,
                           @Body actions: DeviceActionsModel
    ): DeviceActionsAnswerModel

    @GET("groups/{group_id}")
    suspend fun getGroupState(@Header("Authorization") token: String): UserInfoResponse

    @POST("groups/{group_id}/actions")
    suspend fun controlGroupActions(@Header("Authorization") token: String,
                                   @Body actions: DeviceActionsModel
    ): DeviceActionsAnswerModel

    @POST("scenarios/{scenario_id}/actions")
    suspend fun controlScenarioActions(@Header("Authorization") token: String,
                                    @Body actions: DeviceActionsModel
    ): DeviceActionsAnswerModel

    @DELETE("devices/{device_id}")
    suspend fun deleteDevice(@Header("Authorization") token: String,
                                       @Body actions: DeviceActionsModel
    ): DeviceActionsAnswerModel


}

object RetrofitInstance{

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val yandexApi: YandexApi = retrofit.create(YandexApi::class.java)
}