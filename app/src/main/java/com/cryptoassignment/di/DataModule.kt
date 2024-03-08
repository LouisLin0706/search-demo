package com.cryptoassignment.di

import com.cryptoassignment.base.AppCoDispatcherProvider
import com.cryptoassignment.base.CoDispatcherProvider
import com.cryptoassignment.data.repo.currency.CurrencyRepo
import com.cryptoassignment.data.repo.currency.CurrencyRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(
    value = [
        SingletonComponent::class
    ]
)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindCurrencyRepo(impl: CurrencyRepoImpl): CurrencyRepo

    @Binds
    @Singleton
    abstract fun bindCoDispatcherProvider(impl: AppCoDispatcherProvider): CoDispatcherProvider
}