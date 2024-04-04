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
import com.tatry.yandextest.data.local.entity.device.DeviceRelations

//import com.tatry.yandextest.data.local.entity.device.DeviceRelations

@Dao
interface DeviceDao {
//    @Transaction
//    @Query("SELECT * from device")
//    suspend fun getUserDeviceList(): List<DeviceRelations>

    // Device
    @Query("SELECT * from device")
    suspend fun getDeviceList(): List<DeviceEntity>

    @Transaction
    @Query("SELECT * FROM device")
    suspend fun getAll(): List<DeviceRelations>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDevice(device: DeviceEntity): Long

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertDeviceList(deviceList: List<DeviceEntity>): List<Long>

    @Transaction
    suspend fun insertDeviceWithCapabilityList(
        device: DeviceEntity, capabilityList: List<DeviceCapabilityEntity>) {
        val devId = insertDevice(device)
        capabilityList.forEach { it.generatedId = devId }
        insertDeviceCapabilityList(capabilityList)
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertDeviceCapabilityList(deviceList: List<DeviceCapabilityEntity>)



    // DeviceCapability
    @Query("SELECT * from device_capability")
    suspend fun getDeviceCapabilityList(): List<DeviceCapabilityEntity>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertDeviceCapability(deviceCapability: DeviceCapabilityEntity)



//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertDeviceCapability(deviceCapabilityEntity: DeviceCapabilityEntity)


}