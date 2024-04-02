package com.tatry.yandextest.domain.model.devices.parameters

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
