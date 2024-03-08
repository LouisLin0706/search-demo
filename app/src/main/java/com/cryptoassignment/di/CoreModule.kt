package com.cryptoassignment.di

import android.content.Context
import com.cryptoassignment.local.AppDatabase
import com.cryptoassignment.local.currency.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(
    value = [
        SingletonComponent::class
    ]
)
class CoreModule {
    
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return AppDatabase.getInstance(applicationContext = applicationContext)
    }

    @Provides
    @Singleton
    fun providesNewsDao(db: AppDatabase): CurrencyDao = db.currencyDao
}