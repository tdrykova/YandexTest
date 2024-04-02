package com.tatry.yandextest.domain.model.devices.user_info

import com.tatry.yandextest.domain.model.devices.action.StateObjectModel

data class CapabilityGroupModel(
    val retrievable: Boolean,
    val type: String,
    val parameters: Any,
    val state: StateObjectModel? = null
)