package com.tatry.yandextest.domain.repository

import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionModel
import com.tatry.yandextest.domain.model.devices.action.DeviceListModel
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.request.DeviceActionsModel
import com.tatry.yandextest.domain.model.devices.user_info.UserInfoModel
import com.tatry.yandextest.domain.model.user.UserInfoResponse
import com.tatry.yandextest.domain.model.user.UserModel

interface YandexRepository {
    // Network
    suspend fun getUserInfoFromNetwork(token: String) : UserInfoModel
    suspend fun getDeviceStateFromNetwork(token: String, devId: String) : GetDeviceStateResponse
    suspend fun controlDevicesActionsFromNetwork(token: String, deviceList: DeviceListModel): DeviceActionsAnswerModel

    // Local
    suspend fun saveUserToDb(userModel: UserModel)
    suspend fun saveDeviceToDb(deviceModel: DeviceModel)
    suspend fun saveDeviceListToDb(deviceModelList: List<DeviceModel>)

    suspend fun getUserInfoFromDb(token: String) : UserInfoResponse

    suspend fun getDeviceFromDb(id: String): DeviceModel
    suspend fun getDeviceListFromDb(): List<DeviceModel>
    suspend fun getDeviceStateFromDb(token: String, devId: String) : GetDeviceStateResponse
    suspend fun controlDevicesActionsFromDb(token: String, actions: DeviceActionsModel): DeviceActionsAnswerModel
}