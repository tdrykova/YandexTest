package com.tatry.yandextest.domain.usecase.network

import com.tatry.yandextest.domain.model.devices.user_info.UserInfoModel
import com.tatry.yandextest.domain.repository.YandexRepository


class UploadUserInfoUseCase(private val repo: YandexRepository) {
    suspend operator fun invoke(token: String) : UserInfoModel {
        return repo.getUserInfoFromNetwork(token)
    }
}


