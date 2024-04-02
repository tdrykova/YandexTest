package com.tatry.yandextest.data.network.dto.user_info

import com.google.gson.annotations.SerializedName

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


