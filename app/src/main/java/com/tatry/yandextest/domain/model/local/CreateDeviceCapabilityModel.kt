package com.tatry.yandextest.domain.model.local

data class CreateDeviceCapabilityModel(
    val reportable: Boolean? = null,
    val retrievable: Boolean? = null,
    val type: String,
)
