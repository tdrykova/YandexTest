package com.tatry.yandextest.data.local.entity.device

//import android.arch.persistence.room.Entity
import androidx.room.Entity

@Entity(tableName = "state_object")
data class StateObjectEntity(
    val instance: String,
    val value: Any
)

