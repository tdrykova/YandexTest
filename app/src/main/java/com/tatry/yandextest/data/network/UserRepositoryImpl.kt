package com.tatry.yandextest.data.network

import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.request.DeviceActionsModel
import com.tatry.yandextest.domain.model.user.UserInfoResponse
import com.tatry.yandextest.domain.repository.YandexRepository

object UserRepositoryImpl: YandexRepository {
//    private val mapper = UserInfoMapper()
    override suspend fun getUserInfo(token: String): UserInfoResponse {
//        return mapper.mapDtoToModel(RetrofitInstance.yandexApi.getUserInfo(token))
        return RetrofitInstance.yandexApi.getUserInfo(token)
    }


    override suspend fun getDeviceState(token: String, devId: String): GetDeviceStateResponse {
        return RetrofitInstance.yandexApi.getDeviceState(token, devId)
    }

    override suspend fun postDevicesActions(token: String, actions: DeviceActionsModel):
            DeviceActionsAnswerModel {
        return RetrofitInstance.yandexApi.controlDeviceActions(token, actions)
    }
}