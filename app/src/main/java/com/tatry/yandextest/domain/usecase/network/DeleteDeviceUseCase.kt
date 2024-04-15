package com.tatry.yandextest.domain.usecase.network

import com.tatry.yandextest.domain.model.devices.ResponseModel
import com.tatry.yandextest.domain.repository.YandexRepository

class DeleteDeviceUseCase(private val repo: YandexRepository) {
    suspend fun postDevicesActions(token: String, devId: String) : ResponseModel {
        return repo.deleteDevice(token, devId)
    }
}