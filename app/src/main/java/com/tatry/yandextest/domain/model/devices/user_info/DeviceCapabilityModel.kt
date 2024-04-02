package com.tatry.yandextest.domain.model.devices.user_info

import com.tatry.yandextest.domain.model.devices.action.StateObjectModel

data class DeviceCapabilityModel(
    val type: String,
    val reportable: Boolean,
    val retrievable: Boolean,
    val parameters: Any, //
    val state: StateObjectModel? = null, //
    val lastUpdated: Float
)