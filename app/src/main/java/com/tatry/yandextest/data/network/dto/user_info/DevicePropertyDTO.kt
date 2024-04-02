package com.tatry.yandextest.data.network.dto.user_info

import com.google.gson.annotations.SerializedName
import com.tatry.yandextest.data.network.dto.action.StateObjectDTO

data class DevicePropertyDTO(
    @SerializedName("type")
    val type: String,
    @SerializedName("reportable")
    val reportable: Boolean,
    @SerializedName("retrievable")
    val retrievable: Boolean,
    @SerializedName("parameters")
    val parameters: Any, //
    @SerializedName("state")
    val state: StateObjectDTO? = null, //
    @SerializedName("lastUpdated")
    val lastUpdated: Float
)