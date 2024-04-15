package com.tatry.yandextest.domain.usecase.local

import com.tatry.yandextest.domain.model.devices.user_info.DeviceCapabilityModel
import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.repository.YandexRepository

class InsertDeviceWithCapabilityListUseCase(private val repo: YandexRepository) {
    suspend operator fun invoke(device: DeviceModel, capabilityList: List<DeviceCapabilityModel>) =
        repo.insertDeviceWithCapabilityList(device, capabilityList)
}