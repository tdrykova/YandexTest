package com.tatry.yandextest.domain.model.devices.answer

data class DeviceActionsAnswerModel(
    val devices: List<Device> = listOf(),
    val request_id: String = "request_id",
    val status: String = "status"
)

data class Device(
    val capabilities: List<Capability>,
    val id: String
)

data class Capability(
    val state: State,
    val type: String
)

data class State(
    val action_result: ActionResult,
    val instance: String
)

data class ActionResult(
    val status: String,
    val error_code: String,
    val error_message: String
)