package com.tatry.yandextest.domain.model.devices

import com.tatry.yandextest.domain.model.devices.parameters.CapabilityParams

/**
 * Объект [DeviceCapabilityObject] описывает умение устройства.
 * [Получение полной информации об умном доме пользователя](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-user-info.html)
 * [Получение информации о состоянии устройства](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-device-info.html)
 * @property [reportable] Оповещает ли умение об изменении состояния платформу умного дома, используя сервис уведомлений.
 * @property [retrievable] Доступен ли для данного умения устройства запрос состояния.
 * @property [type] [Тип умения](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/capability-types.html#capability-types__types)
 * @property [parameters] Параметры умения.
 * Соответствует значению поля parameters из [описания умения](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/on_off.html#state__example).
 * @property [state] Текущее состояние умения.
 * Если значение null , значит состояние никогда не передавалось в систему.
 * Соответствует значению поля state из [описания умения](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/on_off.html#state__example).
 * @property [lastUpdated] Время последнего обновления состояния в секундах, формат unix timestamp.
 */
data class DeviceCapabilityObject(
    val reportable: Boolean,
    val retrievable: Boolean,
    val type: String,
    val parameters: CapabilityParams, // фиксированные значения для умения (capability)
    val state: StateObject? = null, // default
    val lastUpdated: Float
)
