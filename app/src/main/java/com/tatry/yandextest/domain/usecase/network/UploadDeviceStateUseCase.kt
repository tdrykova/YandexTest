package com.tatry.yandextest.domain.usecase.network

import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.repository.YandexRepository

class UploadDeviceStateUseCase(private val repo: YandexRepository) {
    suspend fun getDeviceStateUseCase(token: String, devId: String) : GetDeviceStateResponse {
        return repo.getDeviceStateFromNetwork(token, devId)
    }
}