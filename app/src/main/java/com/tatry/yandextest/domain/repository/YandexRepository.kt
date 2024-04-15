package com.tatry.yandextest.domain.repository

import com.tatry.yandextest.data.local.entity.device.DeviceRelations
import com.tatry.yandextest.domain.model.devices.ResponseModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionsRequestModel
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.user_info.DeviceCapabilityModel
import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.model.devices.user_info.UserInfoModel
import com.tatry.yandextest.domain.model.local.CreateDeviceCapabilityModel
import com.tatry.yandextest.domain.model.user.UserModel

interface YandexRepository {
    // Network
    suspend fun getUserInfoFromNetwork(token: String) : UserInfoModel
    suspend fun getDeviceStateFromNetwork(token: String, devId: String) : GetDeviceStateResponse
    suspend fun controlDevicesActionsFromNetwork(token: String, deviceList: DeviceActionsRequestModel): DeviceActionsAnswerModel
    suspend fun deleteDevice(token: String, devId: String): ResponseModel

    // Local
    suspend fun saveUserToDb(userModel: UserModel)
    suspend fun saveDeviceToDb(deviceModel: DeviceModel)
    suspend fun saveDeviceListToDb(deviceModelList: List<DeviceModel>)

    suspend fun getAll(): List<DeviceRelations>




    // DeviceCapability
//    suspend fun saveDeviceCapabilityToDb(devId: String, deviceCapabilityModel: DeviceCapabilityModel)
    suspend fun createDeviceCapabilityListToDb(createDeviceCapabilityModelList: List<CreateDeviceCapabilityModel>)

    suspend fun insertDeviceWithCapabilityList(device: DeviceModel, capabilityList: List<DeviceCapabilityModel>)

    suspend fun getUserInfoFromDb(token: String) : UserInfoModel

    suspend fun getDeviceFromDb(id: String): DeviceModel
    suspend fun getDeviceListFromDb(): List<DeviceModel>

    suspend fun getDeviceCapabilityFromDb(devId: String, type: String): DeviceCapabilityModel
    suspend fun getDeviceCapabilityListFromDb(devId: String,): List<DeviceCapabilityModel>
    suspend fun getDeviceStateFromDb(token: String, devId: String) : GetDeviceStateResponse
    suspend fun controlDevicesActionsFromDb(token: String, actions: DeviceActionsRequestModel): DeviceActionsAnswerModel
}