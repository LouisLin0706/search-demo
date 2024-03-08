package com.cryptoassignment.data.repo.currency

import com.cryptoassignment.data.model.CurrencyModel
import com.cryptoassignment.local.currency.Type
import kotlinx.coroutines.flow.Flow

interface CurrencyRepo {
    fun getCurrencyList(searchKeyWords: String, types: List<Type>): Flow<List<CurrencyModel>>
    suspend fun clearLocal()
    suspend fun createLocalSet()
}