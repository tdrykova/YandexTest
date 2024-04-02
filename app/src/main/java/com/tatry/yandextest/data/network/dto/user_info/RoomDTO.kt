package com.tatry.yandextest.data.network.dto.user_info

import com.google.gson.annotations.SerializedName

data class RoomDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("household_id")
    val householdId: String,
    @SerializedName("devices")
    val deviceIdList: List<String>
)