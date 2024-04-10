package com.tatry.yandextest.presentation.control

data class WidgetModel(
    var id: Int? = null,
    val title: String,
    val capabilityType: String,
    val capabilitySubType: String,
    val widgetType: String,
    val posX: Int,
    val posY: Int,
    val width: Int,
    val height: Int,
)