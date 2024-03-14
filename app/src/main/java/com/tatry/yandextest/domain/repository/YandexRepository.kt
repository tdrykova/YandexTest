package com.tatry.yandextest.domain.repository

import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.request.DeviceActionsModel
import com.tatry.yandextest.domain.model.user.UserInfoResponse

interface YandexRepository {
    suspend fun getUserInfo(token: String) : UserInfoResponse
    suspend fun getDeviceState(token: String, devId: String) : GetDeviceStateResponse
    suspend fun postDevicesActions(token: String, actions: DeviceActionsModel): DeviceActionsAnswerModel
}