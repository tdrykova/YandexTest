package com.tatry.yandextest.data.network.dto.action

import com.google.gson.annotations.SerializedName

data class DeviceActionsRequestDTO(
    @SerializedName("devices")
    val devices: List<DeviceActionDTO>
)

data class DeviceActionDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("actions")
    val actions: List<ActionObjectDTO>,
)

data class ActionObjectDTO(
    @SerializedName("type")
    val type: String,
    @SerializedName("state")
    val state: StateObjectDTO? = null
)

data class StateObjectDTO (
    @SerializedName("instance")
    val instance: String?,
    @SerializedName("value")
    val value: Any?
)