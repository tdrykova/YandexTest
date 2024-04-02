package com.tatry.yandextest.data.network.dto.user_info

import com.google.gson.annotations.SerializedName
import com.tatry.yandextest.data.network.dto.action.StateObjectDTO

data class CapabilityGroupDTO(
    @SerializedName("retrievable")
    val retrievable: Boolean,
    @SerializedName("type")
    val type: String,
    @SerializedName("parameters")
    val parameters: Any,
    @SerializedName("state")
    val state: StateObjectDTO? = null
)