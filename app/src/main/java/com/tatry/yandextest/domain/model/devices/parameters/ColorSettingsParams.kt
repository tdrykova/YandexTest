package com.tatry.yandextest.domain.model.devices.parameters

import com.tatry.yandextest.domain.model.devices.parameters.color.ColorScene
import com.tatry.yandextest.domain.model.devices.parameters.color.TemperatureK

data class ColorSettingsParams(
    val colorModel: String,
    val temperatureK: TemperatureK,
    val colorScene: ColorScene
): CapabilityParams()
