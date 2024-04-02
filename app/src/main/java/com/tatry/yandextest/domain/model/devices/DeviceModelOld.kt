package com.tatry.yandextest.domain.model.devices

data class DeviceModelOld(
    val id: String,
    val name: String,
  //  val aliases: List<String>,
    val type: String, // category
    val external_id: String,
//    val skill_id: String,
    val household_id: String,
    val room: String,
    val groups: List<String>,
    val actions: List<CapabilityObject>,
//    val properties: List<Any>
    )

data class CapabilityObject(
    val type: String,
    val parameters: Any,
    val state: State
)

data class State (
    val instance: String,
    val value: Any,
    val relative: Boolean
)

data class CapabilityDevice(
    val reportable: Boolean,
    val retrievable: Boolean,
    val type: String,
    val parameters: Any,
    val state: State,
//    val lastUpdated: Float
)

