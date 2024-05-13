package com.tatry.yandextest.data.network.dto.user_info

import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.tatry.yandextest.data.network.dto.action.StateObjectDTO

data class UserInfoDTO(
    @SerializedName("status")
    val status: String,
    @SerializedName("request_id")
    val requestId: String,
    @SerializedName("rooms")
    val roomList: List<RoomDTO> = listOf(),
    @SerializedName("groups")
    val groupList: List<GroupDTO> = listOf(),
    @SerializedName("devices")
    val deviceList: List<DeviceDTO> = listOf(),
    @SerializedName("scenarios")
    val scenarioList: List<ScenarioDTO> = listOf(),
    @SerializedName("households")
    val householdList: List<HouseholdDTO> = listOf()
)


fun UserInfoDTO.toJson(): String {
    val gson = GsonBuilder()
        .create()
    return gson.toJson(this)
}

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

data class DevicePropertyDTO(
    @SerializedName("type")
    val type: String,
    @SerializedName("reportable")
    val reportable: Boolean,
    @SerializedName("retrievable")
    val retrievable: Boolean,
    @SerializedName("parameters")
    val parameters: Any,
    @SerializedName("state")
    val state: StateObjectDTO? = null,
    @SerializedName("lastUpdated")
    val lastUpdated: Float
)

data class DeviceCapabilityDTO(
    @SerializedName("type")
    val type: String,
    @SerializedName("reportable")
    val reportable: Boolean,
    @SerializedName("retrievable")
    val retrievable: Boolean,
    @SerializedName("parameters")
    val parameters: Any,
    @SerializedName("state")
    val state: StateObjectDTO? = null,
    @SerializedName("lastUpdated")
    val lastUpdated: Double
)

data class ScenarioDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("is_active")
    val isActive: Boolean
)

data class HouseholdDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)


