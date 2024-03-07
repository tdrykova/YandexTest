package com.tatry.yandextest.ui

sealed class ProgressState {
    object Loading : ProgressState()
    object Success : ProgressState()
}