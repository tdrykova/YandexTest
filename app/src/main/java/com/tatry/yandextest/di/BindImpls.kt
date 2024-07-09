package com.tatry.yandextest.di

import com.tatry.yandextest.data.YandexRepositoryImpl
import com.tatry.yandextest.domain.repository.YandexRepository
import dagger.Binds
import dagger.Module

@Module
interface BindImpls {

    @Binds
    fun bindYandexRepo(
        yandexRepositoryImpl: YandexRepositoryImpl
    ): YandexRepository
}