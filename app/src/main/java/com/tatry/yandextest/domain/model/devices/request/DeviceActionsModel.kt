package com.tatry.yandextest.domain.model.devices.request

data class DeviceActionsModel(
    val devices: List<Device>
)

data class Device(
    val id: String,
    val actions: List<Action>,
)

data class Action(
    val type: String,
    val state: State
)

data class State (
    val instance: String,
    val value: Any,
    val relative: Boolean
)