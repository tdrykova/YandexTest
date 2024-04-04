package com.tatry.yandextest.data.local.entity.device

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Модель [DeviceEntity] в БД.
 * На основе DeviceModel из domain
 */
@Entity(tableName = "device",
indices = [Index(value = ["external_id"], unique = true)])
data class DeviceEntity(
    @PrimaryKey
    val generatedId: Long,
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
