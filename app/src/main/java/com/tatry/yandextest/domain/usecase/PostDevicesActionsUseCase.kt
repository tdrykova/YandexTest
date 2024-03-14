package com.tatry.yandextest.domain.usecase

import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.request.DeviceActionsModel
import com.tatry.yandextest.domain.repository.YandexRepository


class PostDevicesActionsUseCase(private val repo: YandexRepository) {
    suspend fun postDevicesActions(token: String = "", actions: DeviceActionsModel) : DeviceActionsAnswerModel {
        return repo.postDevicesActions(token, actions)
    }
}