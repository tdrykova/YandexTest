package com.tatry.yandextest

import android.app.Application
import com.tatry.yandextest.data.local.database.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        db = AppDatabase.getInstance(this)
    }

    companion object {
        lateinit var INSTANCE: App
            private set
    }
}