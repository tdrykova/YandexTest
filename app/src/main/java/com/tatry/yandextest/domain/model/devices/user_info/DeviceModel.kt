package com.tatry.yandextest.domain.model.devices.user_info

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