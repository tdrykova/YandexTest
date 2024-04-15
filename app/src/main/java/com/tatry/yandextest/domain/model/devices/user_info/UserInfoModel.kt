package com.tatry.yandextest.domain.model.devices.user_info

import com.tatry.yandextest.domain.model.devices.action.StateObjectModel

data class UserInfoModel(
    val status: String = "status",
    val requestId: String = "request_id",
    val roomList: List<RoomModel> = listOf(),
    val groupList: List<GroupModel> = listOf(),
    val deviceList: List<DeviceModel> = listOf(),
    val scenarioList: List<ScenarioModel> = listOf(),
    val householdList: List<HouseholdModel> = listOf()
)

data class RoomModel(
    val id: String,
    val name: String,
    val householdId: String,
    val deviceIdList: List<String>
)

data class GroupModel(
    val id: String,
    val name: String,
    val aliasesList: List<String>,
    val householdId: String,
    val type: String,
    val deviceIdList: List<String>,
    val capabilityList: List<CapabilityGroupModel>
)

data class CapabilityGroupModel(
    val retrievable: Boolean,
    val type: String,
    val parameters: Any,
    val state: StateObjectModel? = null
)

data class DeviceModel(
    val generatedId: Long,
    val id: String,
    val name: String,
    val aliasesList: List<String>,
    val roomId: String? = null,
    val externalId: String,
    val skillId: String,
    val type: String,
    val groupIdList: List<String>,
    val capabilityList: List<DeviceCapabilityModel>,
    val propertyList: List<DevicePropertyModel>,
    val householdId: String,
)

data class DevicePropertyModel(
    val type: String,
    val reportable: Boolean,
    val retrievable: Boolean,
    val parameters: Any,
    val state: StateObjectModel? = null,
    val lastUpdated: Float
)

data class DeviceCapabilityModel(
    val generatedId: Long,
    val devId: String,
    val type: String,
    val reportable: Boolean,
    val retrievable: Boolean,
    val parameters: Any,
    val state: StateObjectModel? = null,
    val lastUpdated: Double
)

data class ScenarioModel(
    val id: String,
    val name: String,
    val isActive: Boolean
)

data class HouseholdModel(
    val id: String,
    val name: String
)

