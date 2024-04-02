package com.tatry.yandextest.data.local.database

import android.app.Application
//import android.arch.persistence.room.Database
//import android.arch.persistence.room.Room
//import android.arch.persistence.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tatry.yandextest.data.local.dao.DeviceDao
import com.tatry.yandextest.data.local.entity.device.DeviceCapabilityEntity
import com.tatry.yandextest.data.local.entity.device.DeviceEntity

@Database(entities = [DeviceEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun deviceDao() : DeviceDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "app.db"
        private val LOCK = Any()
        fun getInstance(application: Application): AppDatabase {

            INSTANCE?.let {db->
                return db
            } // check 1

            synchronized(LOCK) {
                INSTANCE?.let {db->
                    return db
                } // check 2

                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }


        }
    }


}