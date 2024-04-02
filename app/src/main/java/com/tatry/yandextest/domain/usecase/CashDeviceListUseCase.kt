package com.tatry.yandextest.domain.usecase

import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.repository.YandexRepository

class CashDeviceListUseCase(private val repo: YandexRepository) {
    suspend operator fun invoke(deviceListModel: List<DeviceModel>) = repo.saveDeviceListToDb(deviceListModel)
}