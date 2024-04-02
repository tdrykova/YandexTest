package com.tatry.yandextest.domain.model.devices.user_info

data class GroupModel(
    val id: String,
    val name: String,
    val aliasesList: List<String>,
    val householdId: String,
    val type: String,
    val deviceIdList: List<String>,
    val capabilityList: List<CapabilityGroupModel>
)