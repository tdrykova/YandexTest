package com.tatry.yandextest.di

import com.tatry.yandextest.domain.usecase.local.CashDeviceListUseCase
import com.tatry.yandextest.domain.usecase.local.CreateDeviceCapabilityListUseCase
import com.tatry.yandextest.domain.usecase.local.GetAllDeviceListUseCase
import com.tatry.yandextest.domain.usecase.local.GetDeviceListUseCase
import com.tatry.yandextest.domain.usecase.local.InsertDeviceWithCapabilityListUseCase
import com.tatry.yandextest.domain.usecase.network.PostDevicesActionsUseCase
import com.tatry.yandextest.domain.usecase.network.UploadDeviceStateUseCase
import com.tatry.yandextest.domain.usecase.network.UploadUserInfoUseCase
import com.tatry.yandextest.presentation.YandexViewModel
import com.tatry.yandextest.presentation.YandexViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideYandexViewModel(
        uploadUserInfoUseCase: UploadUserInfoUseCase,
        cashDeviceListUseCase: CashDeviceListUseCase,
        createDeviceCapabilityListUseCase: CreateDeviceCapabilityListUseCase,
        getDeviceListUseCase: GetDeviceListUseCase,
        uploadDeviceStateUseCase: UploadDeviceStateUseCase,
        postDevicesActionsUseCase: PostDevicesActionsUseCase,
        insertDeviceWithCapabilityListUseCase: InsertDeviceWithCapabilityListUseCase,
        getAllDeviceListUseCase: GetAllDeviceListUseCase,
    ): YandexViewModel {
        return YandexViewModel(
            uploadUserInfoUseCase = uploadUserInfoUseCase,
            cashDeviceListUseCase = cashDeviceListUseCase,
            createDeviceCapabilityListUseCase = createDeviceCapabilityListUseCase,
            getDeviceListUseCase = getDeviceListUseCase,
            uploadDeviceStateUseCase = uploadDeviceStateUseCase,
            postDevicesActionsUseCase = postDevicesActionsUseCase,
            insertDeviceWithCapabilityListUseCase = insertDeviceWithCapabilityListUseCase,
            getAllDeviceListUseCase = getAllDeviceListUseCase,
        )
    }

    @Provides
    fun provideYandexViewModelFactory(
        yandexViewModel: YandexViewModel
    ): YandexViewModelFactory {
        return YandexViewModelFactory(yandexViewModel)
    }
}