package com.tatry.yandextest.domain.model.devices.get_device_state

data class GetDeviceStateResponse(
    val status: String = "status",
    val request_id: String = "request_id",
    val id: String = "id",
    val name: String = "name",
    val aliases: List<String> = listOf(),
    val type: String = "type",
    val state: String = "state",
    val groups: List<String> = listOf(),
    val room: String = "room",
    val external_id: String = "external_id",
    val skill_id: String = "skill_id",
    val capabilities: List<Capability> = listOf(),
    val properties: List<Property> = listOf()
)

data class Capability(
    val retrievable: Boolean,
    val type: String, // on_off
    val parameters: Any,
    val state: Any,
    val last_updated: Float
)

data class Property(
    val retrievable: Boolean,
    val type: String,
    val parameters: Any,
    val state: Any,
    val last_updated: Float,
)


