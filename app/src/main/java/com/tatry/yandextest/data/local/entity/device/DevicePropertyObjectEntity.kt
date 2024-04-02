package com.tatry.yandextest.data.local.entity.device

//import android.arch.persistence.room.Entity
import androidx.room.Entity

@Entity(tableName = "device_property_object")
data class DevicePropertyObjectEntity(
    val reportable: Boolean,
    val retrievable: Boolean,
    val type: String,
    val parameters: Any, // фиксированные значения для свойства (property)
    val state: StateObjectEntity,
    val lastUpdated: Float
)
