package com.tatry.yandextest.domain.model.devices.action

data class ActionObjectModel(
    val type: String,
    val state: StateObjectModel? = null
)