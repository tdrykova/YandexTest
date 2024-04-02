package com.tatry.yandextest.data.local.entity.device.parameters


data class ColorSettingsParameters(
    val colorModel: String,
    val temperatureK: TemperatureK,
    val colorScene: ColorScene
)

data class TemperatureK(
    val min: Int,
    val max: Int
)

data class ColorScene(
    val scenes: List<Scene>
)

data class Scene(
    val id: String
)
