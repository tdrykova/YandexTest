package com.tatry.yandextest.di

import android.app.Application
import com.tatry.yandextest.data.YandexRepositoryImpl
import com.tatry.yandextest.data.local.dao.DeviceDao
import com.tatry.yandextest.data.mapper.DeviceDTOMapper
import com.tatry.yandextest.data.mapper.DeviceEntityMapper
import com.tatry.yandextest.domain.repository.YandexRepository
import com.tatry.yandextest.domain.usecase.local.CashDeviceListUseCase
import com.tatry.yandextest.domain.usecase.local.CreateDeviceCapabilityListUseCase
import com.tatry.yandextest.domain.usecase.local.GetAllDeviceListUseCase
import com.tatry.yandextest.domain.usecase.local.GetDeviceListUseCase
import com.tatry.yandextest.domain.usecase.local.InsertDeviceWithCapabilityListUseCase
import com.tatry.yandextest.domain.usecase.network.DeleteDeviceUseCase
import com.tatry.yandextest.domain.usecase.network.PostDevicesActionsUseCase
import com.tatry.yandextest.domain.usecase.network.UploadDeviceStateUseCase
import com.tatry.yandextest.domain.usecase.network.UploadUserInfoUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideYandexRepositoryImpl(
        deviceDTOMapper: DeviceDTOMapper,
        deviceEntityMapper: DeviceEntityMapper,
        deviceDao: DeviceDao
    ): YandexRepositoryImpl {
        return YandexRepositoryImpl(
            deviceDtoMapper = deviceDTOMapper,
            deviceEntityMapper = deviceEntityMapper,
            deviceDao = deviceDao
        )
    }

    // связали Impl с repo -> используем repo, при вызове repo будет автоматически подставляться Impl
    // Network
    @Provides
    fun provideDeleteDeviceUseCase(
        yandexRepository: YandexRepository
    ): DeleteDeviceUseCase {
        return DeleteDeviceUseCase(yandexRepository)
    }

    @Provides
    fun providePostDevicesActionsUseCase(
        yandexRepository: YandexRepository
    ): PostDevicesActionsUseCase {
        return PostDevicesActionsUseCase(yandexRepository)
    }

    @Provides
    fun provideUploadDeviceStateUseCase(
        yandexRepository: YandexRepository
    ): UploadDeviceStateUseCase {
        return UploadDeviceStateUseCase(yandexRepository)
    }

    @Provides
    fun provideUploadUserInfoUseCase(
        yandexRepository: YandexRepository
    ): UploadUserInfoUseCase {
        return UploadUserInfoUseCase(yandexRepository)
    }

    // Local
    @Provides
    fun provideCashDeviceListUseCase(
        yandexRepository: YandexRepository
    ): CashDeviceListUseCase {
        return CashDeviceListUseCase(yandexRepository)
    }

    @Provides
    fun provideCreateDeviceCapabilityListUseCase(
        yandexRepository: YandexRepository
    ): CreateDeviceCapabilityListUseCase {
        return CreateDeviceCapabilityListUseCase(yandexRepository)
    }

    @Provides
    fun provideGetAllDeviceListUseCase(
        yandexRepository: YandexRepository
    ): GetAllDeviceListUseCase {
        return GetAllDeviceListUseCase(yandexRepository)
    }

    @Provides
    fun provideGetDeviceListUseCase(
        yandexRepository: YandexRepository
    ): GetDeviceListUseCase {
        return GetDeviceListUseCase(yandexRepository)
    }

    @Provides
    fun provideInsertDeviceWithCapabilityListUseCase(
        yandexRepository: YandexRepository
    ): InsertDeviceWithCapabilityListUseCase {
        return InsertDeviceWithCapabilityListUseCase(yandexRepository)
    }
}