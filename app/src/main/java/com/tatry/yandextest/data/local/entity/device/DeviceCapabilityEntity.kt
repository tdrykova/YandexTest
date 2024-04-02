package com.tatry.yandextest.data.local.entity.device

//import android.arch.persistence.room.ColumnInfo
//import android.arch.persistence.room.Entity
//import android.arch.persistence.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_capability_object")
data class DeviceCapabilityEntity(
    @PrimaryKey
    @ColumnInfo(name = "external_id")
    val externalId: String,
    val reportable: Boolean,
//    val retrievable: Boolean,
//    val type: String,
//    val parameters: Any, // фиксированные значения для умения (capability)
//    val state: StateObjectEntity? = null, // default
//    val lastUpdated: Float
)
