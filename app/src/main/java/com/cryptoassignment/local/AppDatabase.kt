package com.cryptoassignment.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cryptoassignment.local.currency.CurrencyDao
import com.cryptoassignment.local.currency.CurrencyEntity
import com.cryptoassignment.local.currency.CurrencyTypeConverter

@Database(
    entities = [CurrencyEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CurrencyTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val currencyDao: CurrencyDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private const val DB_NAME = "db"
        fun getInstance(applicationContext: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}