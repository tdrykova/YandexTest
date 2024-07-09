package com.tatry.yandextest

import android.app.Application
import com.tatry.yandextest.data.local.database.AppDatabase
import com.tatry.yandextest.di.ApplicationComponent
import com.tatry.yandextest.di.ContextModule
import com.tatry.yandextest.di.DaggerApplicationComponent

class App : Application() {

    lateinit var db: AppDatabase
        private set

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        applicationComponent = DaggerApplicationComponent.builder().contextModule(
            ContextModule(this)
        ).build()

        db = AppDatabase.getInstance(this)
    }

    companion object {
        lateinit var INSTANCE: App
            private set
    }
}