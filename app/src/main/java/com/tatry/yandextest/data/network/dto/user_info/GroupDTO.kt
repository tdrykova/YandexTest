package com.tatry.yandextest.data.network.dto.user_info

import com.google.gson.annotations.SerializedName
import com.tatry.yandextest.domain.model.devices.user_info.CapabilityGroupModel

data class GroupDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("aliases")
    val aliasesList: List<String>,
    @SerializedName("household_id")
    val householdId: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("devices")
    val deviceIdList: List<String>,
    @SerializedName("capabilities")
    val capabilityList: List<CapabilityGroupDTO>
)