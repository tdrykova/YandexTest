package com.tatry.yandextest.domain.model.user


data class UserInfoResponse(
    val status: String = "status",
    val request_id: String = "request_id",
    val rooms: List<Room> = listOf(),
    val groups: List<Group> = listOf(),
    val devices: List<Device> = listOf(),
    val scenarios: List<Scenario> = listOf(),
    val households: List<Household> = listOf()
)

// Room
data class Room(
    val id: String,
    val name: String,
    val household_id: String,
    val devices: List<String>
)

// Group
data class Group(
    val id: String,
    val name: String,
    val aliases: List<String>,
    val household_id: String,
    val type: String,
    val devices: List<String>,
    val capabilities: List<CapabilityGroup>
)

data class CapabilityGroup(
    val retrievable: Boolean,
    val type: String,
    val parameters: Any,
    val state: Any
)

// Device
data class Device(
    val id: String,
    val name: String,
    val aliases: List<String>,
    val type: String,
    val external_id: String,
    val skill_id: String,
    val household_id: String,
    val room: String,
    val groups: List<String>,
    val capabilities: List<CapabilityDevice>,
    val properties: List<Any>
)

data class CapabilityDevice(
    val reportable: Boolean,
    val retrievable: Boolean,
    val type: String,
    val parameters: Any,
    val state: Any,
    val lastUpdated: Float
)

// Scenario
data class Scenario(
    val id: String,
    val name: String,
    val is_active: Boolean
)

data class Household(
    val id: String,
    val name: String
)


