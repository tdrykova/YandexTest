package com.tatry.yandextest.domain.usecase

import com.tatry.yandextest.domain.model.local.CreateDeviceCapabilityModel
import com.tatry.yandextest.domain.repository.YandexRepository

class CreateDeviceCapabilityListUseCase(private val repo: YandexRepository) {
    suspend operator fun invoke(createDeviceCapabilityListModel: List<CreateDeviceCapabilityModel>) =
        repo.createDeviceCapabilityListToDb(createDeviceCapabilityListModel)
}