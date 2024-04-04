package com.tatry.yandextest.data.local.entity.device

import androidx.room.Embedded
import androidx.room.Relation

data class DeviceRelations(
    @Embedded
    val deviceEntity: DeviceEntity,
    @Relation(
        parentColumn = "generatedId",
        entityColumn = "generatedId",
        entity = DeviceCapabilityEntity::class
    )
    val deviceCapabilityList: List<DeviceCapabilityEntity>
)