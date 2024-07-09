package com.tatry.yandextest.di

import android.app.Application
import com.tatry.yandextest.data.local.dao.DeviceDao
import com.tatry.yandextest.data.local.database.AppDatabase
import com.tatry.yandextest.data.mapper.DeviceDTOMapper
import com.tatry.yandextest.data.mapper.DeviceEntityMapper
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideDTOMapperForYandexRepositoryImpl(): DeviceDTOMapper {
        return DeviceDTOMapper()
    }

    @Provides
    fun provideEntityMapperForYandexRepositoryImpl(): DeviceEntityMapper {
        return DeviceEntityMapper()
    }

    @Provides
    fun provideDeviceDao(
        application: Application
    ): DeviceDao {
        return AppDatabase.getInstance(application).deviceDao()
    }

}