package com.tatry.yandextest.data.network.dto.user_info

import com.google.gson.annotations.SerializedName

data class HouseholdDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)