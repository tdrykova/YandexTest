package com.tatry.yandextest.data.local.entity.device

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_capability")
data class DeviceCapabilityEntity(
    var generatedId: Long,
    val reportable: Boolean? = null,
    val retrievable: Boolean? = null,
    @PrimaryKey
    val type: String,
//    val parameters: Any, // фиксированные значения для умения (capability)
//    val state: StateObjectEntity? = null, // default
//    val lastUpdated: Float
)
