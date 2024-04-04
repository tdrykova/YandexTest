package com.tatry.yandextest.domain.usecase

import com.tatry.yandextest.domain.repository.YandexRepository

class GetAllDeviceListUseCase(private val repo: YandexRepository) {
    suspend operator fun invoke() = repo.getAll()
}