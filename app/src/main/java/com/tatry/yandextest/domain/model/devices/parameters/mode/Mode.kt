package com.tatry.yandextest.domain.model.devices.parameters.mode

/**
 * Модель [Mode] относится к [ModeParams]
 * @property [value] Значение режима работы функции, обрабатываемое на стороне провайдера.
 * Допустимые значения можно посмотреть в разделе [Список режимов работы](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/mode-instance-modes.html).
 */
data class Mode(val value: String)
