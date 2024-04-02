package com.tatry.yandextest.data.network.dto.action

import com.google.gson.annotations.SerializedName

data class DeviceListDTO(
    @SerializedName("devices")
    val devices: List<DeviceActionDTO>
)
