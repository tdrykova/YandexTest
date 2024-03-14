package com.tatry.yandextest.data.network.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class UserInfoDto (
    @Json(name="status")
    val status: String,
    @Json(name="request_id")
    val requestId: String,
    @Json(name="rooms")
    val rooms: List<Room>,
    @Json(name="groups")
    val groups: List<Group>,
    @Json(name="devices")
    val devices: List<Device>,
    @Json(name="scenarios")
    val scenarios: List<Scenario>,
    @Json(name="households")
    val households: List<Household>
)


// Room
//@JsonClass(generateAdapter = true)
data class Room(
//    @Json(name="id")
    val id: String,
//    @Json(name="name")
    val name: String,
//    @Json(name="household_id")
    val household_id: String,
//    @Json(name="status")
    val devices: List<String>
)

// Group
@JsonClass(generateAdapter = true)
data class Group(
    @Json(name="id")
    val id: String,
    @Json(name="name")
    val name: String,
    @Json(name="aliases")
    val aliases: List<String>,
    @Json(name="household_id")
    val household_id: String,
    @Json(name="type")
    val type: String,
    @Json(name="devices")
    val devices: List<String>,
    @Json(name="capabilities")
    val capabilities: List<CapabilityGroup>
)

@JsonClass(generateAdapter = true)
data class CapabilityGroup(
    @Json(name="retrievable")
    val retrievable: Boolean,
    @Json(name="type")
    val type: String,
    @Json(name="parameters")
    val parameters: Any,
    @Json(name="state")
    val state: Any
)

// Device
@JsonClass(generateAdapter = true)
data class Device(
    @Json(name="id")
    val id: String,
    @Json(name="name")
    val name: String,
    @Json(name="aliases")
    val aliases: List<String>,
    @Json(name="type")
    val type: String,
    @Json(name="external_id")
    val external_id: String,
    @Json(name="skill_id")
    val skill_id: String,
    @Json(name="household_id")
    val household_id: String,
    @Json(name="room")
    val room: String,
    @Json(name="groups")
    val groups: List<String>,
    @Json(name="capabilities")
    val capabilities: List<CapabilityDevice>,
    @Json(name="properties")
    val properties: List<Any>
)

@JsonClass(generateAdapter = true)
data class CapabilityDevice(
    @Json(name="status")
    val reportable: Boolean,
    @Json(name="status")
    val retrievable: Boolean,
    @Json(name="status")
    val type: String,
    @Json(name="status")
    val parameters: Any,
    @Json(name="status")
    val state: Any,
    @Json(name="status")
    val lastUpdated: Float
)

// Scenario
@JsonClass(generateAdapter = true)
data class Scenario(
    @Json(name="id")
    val id: String,
    @Json(name="name")
    val name: String,
    @Json(name="is_active")
    val is_active: Boolean
)

@JsonClass(generateAdapter = true)
data class Household(
    @Json(name="id")
    val id: String,
    @Json(name="name")
    val name: String
)