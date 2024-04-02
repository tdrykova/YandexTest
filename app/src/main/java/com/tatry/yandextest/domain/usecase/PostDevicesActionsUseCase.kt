package com.tatry.yandextest.domain.usecase

import com.tatry.yandextest.domain.model.devices.action.DeviceListModel
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.repository.YandexRepository

class PostDevicesActionsUseCase(private val repo: YandexRepository) {
    suspend fun postDevicesActions(token: String = "", deviceList: DeviceListModel) : DeviceActionsAnswerModel {
        return repo.controlDevicesActionsFromNetwork(token, deviceList)
    }
}