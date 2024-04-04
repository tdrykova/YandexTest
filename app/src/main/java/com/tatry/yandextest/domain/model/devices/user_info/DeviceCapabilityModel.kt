package com.tatry.yandextest.domain.model.devices.user_info

data class DeviceCapabilityModel(
    val generatedId: Long,
    val devId: String,
    val type: String,
    val reportable: Boolean,
    val retrievable: Boolean,
//    val parameters: Any, //
//    val state: StateObjectModel? = null, //
    val lastUpdated: Double
)