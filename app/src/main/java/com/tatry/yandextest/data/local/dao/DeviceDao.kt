package com.tatry.yandextest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
//import android.arch.persistence.room.Dao
//import android.arch.persistence.room.Insert
//import android.arch.persistence.room.OnConflictStrategy
//import android.arch.persistence.room.Query
//import android.arch.persistence.room.Transaction
import com.tatry.yandextest.data.local.entity.device.DeviceCapabilityEntity
import com.tatry.yandextest.data.local.entity.device.DeviceEntity
//import com.tatry.yandextest.data.local.entity.device.DeviceRelations

@Dao
interface DeviceDao {
//    @Transaction
//    @Query("SELECT * from device")
//    suspend fun getUserDeviceList(): List<DeviceRelations>

    @Query("SELECT * from device")
    suspend fun getDeviceList(): List<DeviceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: DeviceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceList(deviceList: List<DeviceEntity>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertDeviceCapability(deviceCapabilityEntity: DeviceCapabilityEntity)


}