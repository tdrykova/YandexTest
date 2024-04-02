package com.tatry.yandextest.domain.model.devices.parameters

/**
 * Модель поля "parameters" [VideoStreamParams]
 * [Умение video_stream: получение видеопотока с камеры.](https://yandex.ru/dev/dialogs/smart-home/doc/concepts/video_stream.html)
 * @property [protocols] На текущий момент поддерживает только протокол потока hls.
 */
data class VideoStreamParams(
    val protocols: List<String> = listOf("hls")
): CapabilityParams()
