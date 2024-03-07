package com.tatry.yandextest.data.network.mapper

import com.tatry.yandextest.data.network.dto.UserInfoDto
import com.tatry.yandextest.domain.model.user.UserInfoAnswerSuccess


class UserInfoMapper {
    fun mapDtoToModel(userInfoDto: UserInfoDto) = UserInfoAnswerSuccess(
        status = userInfoDto.status,
        request_id = userInfoDto.requestId,
        rooms = userInfoDto.rooms,
        groups = userInfoDto.groups,
        devices = userInfoDto.devices,
        scenarios = userInfoDto.scenarios,
        households = userInfoDto.households
    )

}