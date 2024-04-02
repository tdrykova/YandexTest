package com.tatry.yandextest.domain.model.devices

import com.tatry.yandextest.domain.model.devices.get_device_state.Property
import com.tatry.yandextest.domain.model.user.CapabilityDevice

/**
 * Модель [DeviceModel]
 * [Получение информации о состоянии устройства](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/platform-device-info.html)
 * @property [id] Идентификатор устройства.
 * @property [externalId] Идентификатор устройства в облаке производителя.
 * @property [name] Имя устройства, заданное пользователем.
 * @property [aliases] Массив с дополнительными именами устройства.
 * @property [type] Тип устройства. [Типы устройств](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/device-types.html#device-types__types)
 * @property [state] Состояние устройства. Допустимые значения: online/offline.
 * @property [groups] Массив с идентификаторами групп устройства.
 * @property [capabilities] Массив с информацией об умениях устройства: data class [CapabilityDevice] Описание умения
 * @property [properties] Массив с информацией о свойствах устройства. data class [Property] Описание свойства.
 */
//data class DeviceModel(
//    val id: String,
//    val externalId: String,
//    val name: String,
//    val aliases: List<String>,
//    val type: String,
//    val state: String,
//    val groups: List<String>,
//    val capabilities: List<DeviceCapabilityObject>,
//    val properties: List<DevicePropertyObject>, // такой же, как и DeviceCapabilityObject
//)
