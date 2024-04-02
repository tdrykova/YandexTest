package com.tatry.yandextest.data.network.dto.action

import com.google.gson.annotations.SerializedName

data class StateObjectDTO (
    @SerializedName("instance")
    val instance: String?,
    @SerializedName("value")
    val value: Any?
)



