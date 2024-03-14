package com.tatry.yandextest.domain.usecase

import com.tatry.yandextest.domain.model.user.UserInfoResponse
import com.tatry.yandextest.domain.repository.YandexRepository


class GetUserInfoUseCase(private val repo: YandexRepository) {
    suspend fun getUserInfo(token: String = "") : UserInfoResponse {
        return repo.getUserInfo(token)
    }
}