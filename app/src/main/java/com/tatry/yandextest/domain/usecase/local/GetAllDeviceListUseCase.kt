package com.tatry.yandextest.domain.usecase.local

import com.tatry.yandextest.domain.repository.YandexRepository

class GetAllDeviceListUseCase(private val repo: YandexRepository) {
    suspend operator fun invoke() = repo.getAll()
}