package com.tatry.yandextest.data

import android.app.Application
import com.tatry.yandextest.data.local.database.AppDatabase
import com.tatry.yandextest.data.local.entity.device.DeviceRelations
import com.tatry.yandextest.data.mapper.DeviceEntityMapper
import com.tatry.yandextest.data.mapper.DeviceDTOMapper
import com.tatry.yandextest.data.network.RetrofitInstance
import com.tatry.yandextest.domain.model.devices.ResponseModel
import com.tatry.yandextest.domain.model.devices.action.DeviceActionsRequestModel
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.user_info.DeviceCapabilityModel
import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.model.devices.user_info.UserInfoModel
import com.tatry.yandextest.domain.model.local.CreateDeviceCapabilityModel
import com.tatry.yandextest.domain.model.user.UserModel
import com.tatry.yandextest.domain.repository.YandexRepository

class YandexRepositoryImpl(
    application: Application
): YandexRepository {
    private val deviceDtoMapper = DeviceDTOMapper()
    private val deviceEntityMapper = DeviceEntityMapper()
    private val deviceDao = AppDatabase.getInstance(application).deviceDao()

    // Network
    override suspend fun getUserInfoFromNetwork(token: String): UserInfoModel {
        return deviceDtoMapper.mapUserInfoDTOToUserInfoModel(RetrofitInstance.yandexApi.getUserInfo(token))
    }

    override suspend fun controlDevicesActionsFromNetwork(token: String, deviceList: DeviceActionsRequestModel):
            DeviceActionsAnswerModel {
        return RetrofitInstance.yandexApi.controlDeviceActions(
            token,
            deviceDtoMapper.mapDeviceListModelToDeviceListDTO(deviceList))
    }

    override suspend fun getDeviceStateFromNetwork(token: String, devId: String): GetDeviceStateResponse {
        return RetrofitInstance.yandexApi.getDeviceState(token, devId)
    }



    override suspend fun deleteDevice(token: String, devId: String): ResponseModel {
        return RetrofitInstance.yandexApi.deleteDevice(token, devId)
    }


    // Local Room Database
    override suspend fun saveUserToDb(userModel: UserModel) {

    }

    override suspend fun saveDeviceToDb(deviceModel: DeviceModel) {
        deviceDao.insertDevice(deviceDtoMapper.mapDeviceModelToDeviceEntity(deviceModel))
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
            deviceDtoMapper.mapDeviceModelToDeviceEntity(device),
            deviceEntityMapper.mapCreateDeviceCapabilityModelListToDeviceCapabilityEntityList(capabilityList))
    }

    override suspend fun getUserInfoFromDb(token: String): UserInfoModel {
        TODO("Not yet implemented")
    }

    override suspend fun getDeviceFromDb(id: String): DeviceModel {
        TODO("Not yet implemented")
    }

    override suspend fun getDeviceListFromDb(): List<DeviceModel> {
        return deviceDtoMapper.mapDeviceEntityListToDeviceModelList(deviceDao.getDeviceList())
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
        actions: DeviceActionsRequestModel
    ): DeviceActionsAnswerModel {
        TODO("Not yet implemented")
    }
}