package com.tatry.yandextest.presentation

sealed class ProgressState {
    object Loading : ProgressState()
    object Success : ProgressState()
}