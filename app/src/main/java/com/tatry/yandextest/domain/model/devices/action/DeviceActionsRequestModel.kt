package com.tatry.yandextest.domain.model.devices.action

data class DeviceActionsRequestModel(
    val devices: List<DeviceActionModel>
)

data class DeviceActionModel(
    val id: String,
    val actions: List<ActionObjectModel>,
)

data class ActionObjectModel(
    val type: String,
    val state: StateObjectModel? = null
)

data class StateObjectModel (
    val instance: String?,
    val value: Any?
)
