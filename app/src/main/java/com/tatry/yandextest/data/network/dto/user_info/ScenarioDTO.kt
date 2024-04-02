package com.tatry.yandextest.data.network.dto.user_info

import com.google.gson.annotations.SerializedName

data class ScenarioDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("is_active")
    val isActive: Boolean
)