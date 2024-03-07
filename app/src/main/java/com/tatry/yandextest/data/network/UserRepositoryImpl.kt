package com.tatry.yandextest.data.network

import com.tatry.yandextest.data.network.mapper.UserInfoMapper
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.request.DeviceActionsModel
import com.tatry.yandextest.domain.model.user.UserInfoAnswerSuccess
import com.tatry.yandextest.domain.repository.UserRepository

object UserRepositoryImpl: UserRepository {
    private val mapper = UserInfoMapper()
    override suspend fun getUserInfo(token: String): UserInfoAnswerSuccess {
//        return mapper.mapDtoToModel(RetrofitInstance.yandexApi.getUserInfo(token))
        return RetrofitInstance.yandexApi.getUserInfo(token)
    }

    override suspend fun postDevicesActions(token: String, actions: DeviceActionsModel):
            DeviceActionsAnswerModel {
//        return mapper.mapDtoToModel(RetrofitInstance.yandexApi.getUserInfo(token))
        return RetrofitInstance.yandexApi.postDevicesActions(token, actions)
    }
}