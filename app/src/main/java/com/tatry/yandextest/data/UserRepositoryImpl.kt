package com.tatry.yandextest.data

import android.app.Application
import com.tatry.yandextest.data.local.database.AppDatabase
import com.tatry.yandextest.data.local.entity.device.DeviceCapabilityEntity
import com.tatry.yandextest.data.local.entity.device.DeviceEntity
import com.tatry.yandextest.data.local.entity.device.DeviceRelations
import com.tatry.yandextest.data.mapper.DeviceCapabilityEntityMapper
import com.tatry.yandextest.data.mapper.DeviceDTOMapper
import com.tatry.yandextest.data.network.RetrofitInstance
import com.tatry.yandextest.domain.model.devices.action.DeviceListModel
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.request.DeviceActionsModel
import com.tatry.yandextest.domain.model.devices.user_info.DeviceCapabilityModel
import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.model.devices.user_info.UserInfoModel
import com.tatry.yandextest.domain.model.local.CreateDeviceCapabilityModel
import com.tatry.yandextest.domain.model.user.UserInfoResponse
import com.tatry.yandextest.domain.model.user.UserModel
import com.tatry.yandextest.domain.repository.YandexRepository

class UserRepositoryImpl(
    application: Application
): YandexRepository {
    private val mapper = DeviceDTOMapper()
    private val mapper2 = DeviceCapabilityEntityMapper()
    private val deviceDao = AppDatabase.getInstance(application).deviceDao()


    // Network
    override suspend fun getUserInfoFromNetwork(token: String): UserInfoModel {
        return mapper.mapUserInfoDTOToUserInfoModel(RetrofitInstance.yandexApi.getUserInfo(token))
    }

    override suspend fun getDeviceStateFromNetwork(token: String, devId: String): GetDeviceStateResponse {
        return RetrofitInstance.yandexApi.getDeviceState(token, devId)
    }

    override suspend fun controlDevicesActionsFromNetwork(token: String, deviceList: DeviceListModel):
            DeviceActionsAnswerModel {
        return RetrofitInstance.yandexApi.controlDeviceActions(
            token,
            mapper.mapDeviceListModelToDeviceListDTO(deviceList))
    }


    // Local Room Database
    override suspend fun saveUserToDb(userModel: UserModel) {

    }

    override suspend fun saveDeviceToDb(deviceModel: DeviceModel) {
        deviceDao.insertDevice(mapper.mapDeviceModelToDeviceEntity(deviceModel))
    }

    override suspend fun saveDeviceListToDb(deviceModelList: List<DeviceModel>) {
//        deviceDao.insertDeviceList(mapper.mapDeviceModelListToDeviceEntityList(deviceModelList))
    }

    override suspend fun getAll(): List<DeviceRelations> {
        return deviceDao.getAll()
    }


    override suspend fun createDeviceCapabilityListToDb(
        createDeviceCapabilityModelList: List<CreateDeviceCapabilityModel>
    ) {
//        deviceDao.insertDeviceCapabilityList(
//            mapper2.mapCreateDeviceCapabilityModelListToDeviceCapabilityEntityList(
//                createDeviceCapabilityModelList)
//        )
        TODO()
    }

    override suspend fun insertDeviceWithCapabilityList(
        device: DeviceModel,
        capabilityList: List<DeviceCapabilityModel>
    ) {
        deviceDao.insertDeviceWithCapabilityList(
            mapper.mapDeviceModelToDeviceEntity(device),
            mapper2.mapCreateDeviceCapabilityModelListToDeviceCapabilityEntityList(capabilityList))
    }

    override suspend fun getUserInfoFromDb(token: String): UserInfoResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getDeviceFromDb(id: String): DeviceModel {
        TODO("Not yet implemented")
    }

    override suspend fun getDeviceListFromDb(): List<DeviceModel> {
        return mapper.mapDeviceEntityListToDeviceModelList(deviceDao.getDeviceList())
    }

    override suspend fun getDeviceCapabilityFromDb(
        devId: String,
        type: String
    ): DeviceCapabilityModel {
        TODO("Not yet implemented")
    }

    override suspend fun getDeviceCapabilityListFromDb(devId: String): List<DeviceCapabilityModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getDeviceStateFromDb(
        token: String,
        devId: String
    ): GetDeviceStateResponse {
        TODO("Not yet implemented")
    }

    override suspend fun controlDevicesActionsFromDb(
        token: String,
        actions: DeviceActionsModel
    ): DeviceActionsAnswerModel {
        TODO("Not yet implemented")
    }
}