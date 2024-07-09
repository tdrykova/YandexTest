package com.tatry.yandextest.data.mapper

import com.tatry.yandextest.data.local.entity.device.DeviceCapabilityEntity
import com.tatry.yandextest.domain.model.devices.user_info.DeviceCapabilityModel
import com.tatry.yandextest.domain.model.local.CreateDeviceCapabilityModel
import javax.inject.Inject

class DeviceEntityMapper @Inject constructor() {
    fun mapDeviceCapabilityEntityToCreateDeviceCapabilityModel(
        deviceCapabilityEntity: DeviceCapabilityEntity
    ): CreateDeviceCapabilityModel {
        return CreateDeviceCapabilityModel(
            type = deviceCapabilityEntity.type,
            reportable = deviceCapabilityEntity.reportable,
            retrievable = deviceCapabilityEntity.retrievable,
        )
    }

    fun mapDeviceCapabilityEntityListToCreateDeviceCapabilityModelList(
        deviceCapabilityEntityList: List<DeviceCapabilityEntity>) =
        deviceCapabilityEntityList.map { mapDeviceCapabilityEntityToCreateDeviceCapabilityModel(it) }


//    fun mapCreateDeviceCapabilityModelToDeviceCapabilityEntity(
//        createDeviceCapabilityModel: CreateDeviceCapabilityModel
//    ): DeviceCapabilityEntity {
//        return DeviceCapabilityEntity(
//            type = createDeviceCapabilityModel.type,
//            reportable = createDeviceCapabilityModel.reportable,
//            retrievable = createDeviceCapabilityModel.retrievable,
//        )
//    }
//
//    fun mapCreateDeviceCapabilityModelListToDeviceCapabilityEntityList(
//        createDeviceCapabilityModel: List<CreateDeviceCapabilityModel>): List<DeviceCapabilityEntity>{
//        return createDeviceCapabilityModel.map { mapCreateDeviceCapabilityModelToDeviceCapabilityEntity(it) }
//
//    }

    fun mapDeviceCapabilityModelToDeviceCapabilityEntity(
        deviceCapabilityModel: DeviceCapabilityModel
    ): DeviceCapabilityEntity {
        return DeviceCapabilityEntity(
            generatedId = deviceCapabilityModel.generatedId,
//            externalId = deviceCapabilityModel.devId,
            type = deviceCapabilityModel.type,
            reportable = deviceCapabilityModel.reportable,
            retrievable = deviceCapabilityModel.retrievable,
        )
    }

    fun mapCreateDeviceCapabilityModelListToDeviceCapabilityEntityList(
        deviceCapabilityModel: List<DeviceCapabilityModel>): List<DeviceCapabilityEntity>{
        return deviceCapabilityModel.map { mapDeviceCapabilityModelToDeviceCapabilityEntity(it) }

    }
}