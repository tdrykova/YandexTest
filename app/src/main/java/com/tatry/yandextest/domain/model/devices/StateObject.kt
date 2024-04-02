package com.tatry.yandextest.domain.model.devices

/**
 * Объект [StateObject] описывает текущее состояние свойства.
 * [Получение полной информации об умном доме пользователя](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-user-info.html)
 * [Получение информации о состоянии устройства](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-device-info.html)
 * @property [instance] Название функции для данного умения/свойства (capability/property).
 * @property [value] Значение функции для данного умения/свойства (capability/property).
 */
data class StateObject(
    val instance: String,
    val value: Any,
    val capabilityId: Int,
    val lastUpdated: Float
)
