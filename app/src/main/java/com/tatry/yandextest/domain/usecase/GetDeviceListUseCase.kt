package com.tatry.yandextest.domain.usecase

import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.repository.YandexRepository

class GetDeviceListUseCase(private val repo: YandexRepository) {
    suspend operator fun invoke() = repo.getDeviceListFromDb()
}