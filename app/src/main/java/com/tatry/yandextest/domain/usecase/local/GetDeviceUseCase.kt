package com.tatry.yandextest.domain.usecase.local

import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.repository.YandexRepository

class GetDeviceUseCase(private val repo: YandexRepository) {
    suspend operator fun invoke(id: String) = repo.getDeviceFromDb(id)
}