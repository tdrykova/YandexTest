package com.tatry.yandextest.domain.model.devices.user_info

data class UserInfoModel(
    val status: String = "status",
    val requestId: String = "request_id",
    val roomList: List<RoomModel> = listOf(),
    val groupList: List<GroupModel> = listOf(),
    val deviceList: List<DeviceModel> = listOf(),
    val scenarioList: List<ScenarioModel> = listOf(),
    val householdList: List<HouseholdModel> = listOf()
)


