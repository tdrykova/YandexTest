package com.tatry.yandextest.data.network.dto.action

import com.google.gson.annotations.SerializedName


data class DeviceActionDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("actions")
    val actions: List<ActionObjectDTO>,
)



