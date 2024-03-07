package com.tatry.yandextest.domain.usecase

import com.tatry.yandextest.domain.model.user.UserInfoAnswerSuccess
import com.tatry.yandextest.domain.repository.UserRepository


class GetUserInfoUseCase(private val repo: UserRepository) {
    suspend fun getUserInfo(token: String = "") : UserInfoAnswerSuccess {
        return repo.getUserInfo(token)
    }
}