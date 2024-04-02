package com.tatry.yandextest.domain.model.devices

/**
 * Объект [DevicePropertyObject] описывает свойства устройства.
 * [Получение полной информации об умном доме пользователя](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-user-info.html)
 * [Получение информации о состоянии устройства](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-device-info.html)
 * @property [reportable] Оповещает ли свойство об изменении состояния платформу умного дома, используя сервис уведомлений.
 * @property [retrievable] Доступен ли для данного свойства устройства запрос состояния.
 * @property [type] [Тип свойства](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/properties-types.html#properties-types__types)
 * @property [parameters] Параметры свойства.
 * Соответствует значению поля parameters из [описания свойства](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/float.html#discovery__example).
 * @property [state] Текущее состояние свойства.
 * Если значение null , значит состояние никогда не передавалось в систему.
 * Соответствует значению поля state из [описания свойства](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/float.html#discovery__example).
 * @property [lastUpdated] Время последнего обновления состояния в секундах, формат unix timestamp.
 */
data class DevicePropertyObject(
    val reportable: Boolean,
    val retrievable: Boolean,
    val type: String,
    val parameters: Any, // фиксированные значения для свойства (property)
    val state: StateObject,
    val lastUpdated: Float
)
