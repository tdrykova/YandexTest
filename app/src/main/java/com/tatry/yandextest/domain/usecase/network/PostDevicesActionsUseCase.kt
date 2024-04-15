package com.tatry.yandextest.domain.usecase.network

import com.tatry.yandextest.domain.model.devices.action.DeviceActionsRequestModel
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.repository.YandexRepository

class PostDevicesActionsUseCase(private val repo: YandexRepository) {
    suspend fun postDevicesActions(token: String, deviceList: DeviceActionsRequestModel) : DeviceActionsAnswerModel {
        return repo.controlDevicesActionsFromNetwork(token, deviceList)
    }
}