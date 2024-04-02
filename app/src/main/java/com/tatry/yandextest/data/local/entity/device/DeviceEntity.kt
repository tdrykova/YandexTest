package com.tatry.yandextest.data.local.entity.device

//import android.arch.persistence.room.ColumnInfo
//import android.arch.persistence.room.Entity
//import android.arch.persistence.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель [DeviceEntity] в БД.
 * На основе DeviceModel из domain
 */
@Entity(tableName = "device")
data class DeviceEntity(
    @PrimaryKey
    @ColumnInfo(name = "external_id")
    val externalId: String,
    val id: String,
    val name: String,
//    val aliases: List<String>,
    val type: String,
//    val state: String,
//    val groups: List<String>,
//    val capabilities: List<DeviceCapabilityObject>,
//    val properties: List<DevicePropertyObject>,
)
