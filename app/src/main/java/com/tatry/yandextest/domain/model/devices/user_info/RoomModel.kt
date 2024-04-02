package com.tatry.yandextest.domain.model.devices.user_info

data class RoomModel(
    val id: String,
    val name: String,
    val householdId: String,
    val deviceIdList: List<String>
)