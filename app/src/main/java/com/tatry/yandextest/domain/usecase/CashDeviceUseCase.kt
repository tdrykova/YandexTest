package com.tatry.yandextest.domain.usecase

import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.repository.YandexRepository

class CashDeviceUseCase(private val repo: YandexRepository) {
    suspend operator fun invoke(deviceModel: DeviceModel) = repo.saveDeviceToDb(deviceModel)
}