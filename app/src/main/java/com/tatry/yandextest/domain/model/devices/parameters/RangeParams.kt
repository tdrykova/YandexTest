package com.tatry.yandextest.domain.model.devices.parameters

import com.tatry.yandextest.domain.model.devices.parameters.range.Range

data class RangeParams(
    val instance: String,
    val unit: String,
    val randomAccess: Boolean,
    val range: Range
): CapabilityParams()
