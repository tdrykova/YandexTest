package com.tatry.yandextest.data.network.dto.user_info

import com.google.gson.annotations.SerializedName
import com.tatry.yandextest.domain.model.devices.user_info.DeviceCapabilityModel
import com.tatry.yandextest.domain.model.devices.user_info.DevicePropertyModel

data class DeviceDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("aliases")
    val aliasesList: List<String>,
    @SerializedName("room")
    val roomId: String? = null,
    @SerializedName("external_id")
    val externalId: String,
    @SerializedName("skill_id")
    val skillId: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("groups")
    val groupIdList: List<String>,
    @SerializedName("capabilities")
    val capabilityList: List<DeviceCapabilityDTO>,
    @SerializedName("properties")
    val propertyList: List<DevicePropertyDTO>,
    @SerializedName("household_id")
    val householdId: String,
)