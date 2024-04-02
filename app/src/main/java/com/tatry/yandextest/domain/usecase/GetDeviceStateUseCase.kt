package com.tatry.yandextest.domain.usecase

import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.repository.YandexRepository

class GetDeviceStateUseCase(private val repo: YandexRepository) {
    suspend fun getDeviceStateUseCase(token: String, devId: String) : GetDeviceStateResponse {
        return repo.getDeviceStateFromNetwork(token, devId)
    }
}