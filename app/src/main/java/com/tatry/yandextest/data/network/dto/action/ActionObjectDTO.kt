package com.tatry.yandextest.data.network.dto.action

import com.google.gson.annotations.SerializedName

data class ActionObjectDTO(
    @SerializedName("type")
    val type: String,
    @SerializedName("state")
    val state: StateObjectDTO? = null
)