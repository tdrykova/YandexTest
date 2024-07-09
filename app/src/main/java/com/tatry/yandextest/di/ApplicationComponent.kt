package com.tatry.yandextest.di

import com.tatry.yandextest.presentation.YandexViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
        ContextModule::class,
        BindImpls::class
    ]
)
interface ApplicationComponent {

    fun yandexViewModelFactory(): YandexViewModelFactory
}