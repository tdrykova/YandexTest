package com.tatry.yandextest.domain.model.devices.parameters

import com.tatry.yandextest.domain.model.devices.parameters.mode.Mode

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
