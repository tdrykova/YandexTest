package com.tatry.yandextest.domain.repository

import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.request.DeviceActionsModel
import com.tatry.yandextest.domain.model.user.UserInfoAnswerSuccess

interface UserRepository {
    suspend fun getUserInfo(token: String) : UserInfoAnswerSuccess
    suspend fun postDevicesActions(token: String, actions: DeviceActionsModel): DeviceActionsAnswerModel
}