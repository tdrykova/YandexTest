package com.tatry.yandextest.domain.model.devices.parameters

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
    val parameters: CapabilityParams,
    val state: StateObject? = null,
    val lastUpdated: Float
)

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
    val parameters: Any,
    val state: StateObject,
    val lastUpdated: Float
)

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

sealed class CapabilityParams

/**
 * Модель поля "parameters" [OnOffParams]
 * [Умение on_off: удаленное включение и выключение устройства .](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/on_off.html)
 * @property [split] Параметр используется совместно с retrievable:false и показывает,
 * что за включение/выключение устройства у провайдера отвечают разные команды.
 * На главном экране приложения  Дом с Алисой у поддерживаемых устройств будет показана
 * опция включения. Допустимые значения:
 * true — за включение и выключение отвечают разные команды;
 * false — за включение и выключение отвечает одна команда. Является значением по умолчанию.
 */
data class OnOffParams(
    val split: Boolean
): CapabilityParams()

data class ColorSettingsParams(
    val colorModel: String,
    val temperatureK: TemperatureK,
    val colorScene: ColorScene
): CapabilityParams()

data class TemperatureK(
    val min: Int,
    val max: Int
)

data class ColorScene(val scenes: List<Scene>)
data class Scene(val id: String)

data class RangeParams(
    val instance: String,
    val unit: String,
    val randomAccess: Boolean,
    val range: Range
): CapabilityParams()

data class Range(
    val min: Float,
    val max: Float,
    val precision: Float
)

/**
 * Модель поля "parameters" [ModeParams]
 * [Умение mode: переключение режимов работы устройства.](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/mode.html)
 * @property [instance] Название функции для данного умения. Допустимые значения можно посмотреть в
 * разделе [Список функций](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/mode-instance.html).
 * @property [modes] Массив объектов mode, которые описывают режимы работы функции.
 * Минимальное количество режимов в массиве: 1. Ограничение.
 * При повторной отправке массива объектов mode, для одного и того же устройства, необходимо
 * соблюдать порядок режимов. Он должен совпадать с предыдущим отправленным вариантом.
 */
data class ModeParams(
    val instance: String,
    val modes: List<Mode>
): CapabilityParams()

/**
 * Модель [Mode] относится к [ModeParams]
 * @property [value] Значение режима работы функции, обрабатываемое на стороне провайдера.
 * Допустимые значения можно посмотреть в разделе [Список режимов работы](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/mode-instance-modes.html).
 */
data class Mode(val value: String)

/**
 * Модель поля "parameters" [VideoStreamParams]
 * [Умение video_stream: получение видеопотока с камеры.](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/video_stream.html)
 * @property [protocols] На текущий момент поддерживает только протокол потока hls.
 */
data class VideoStreamParams(
    val protocols: List<String> = listOf("hls")
): CapabilityParams()