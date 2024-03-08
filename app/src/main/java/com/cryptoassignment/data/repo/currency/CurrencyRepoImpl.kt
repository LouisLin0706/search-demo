package com.cryptoassignment.data.repo.currency

import android.content.Context
import com.cryptoassignment.base.CoDispatcherProvider
import com.cryptoassignment.data.model.CurrencyJsonModel
import com.cryptoassignment.data.model.CurrencyModel
import com.cryptoassignment.local.currency.CurrencyDao
import com.cryptoassignment.local.currency.CurrencyEntity
import com.cryptoassignment.local.currency.Type
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class CurrencyRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val currencyDao: CurrencyDao,
    private val coDispatcherProvider: CoDispatcherProvider
) : CurrencyRepo {

    override fun getCurrencyList(
        searchKeyWords: String,
        types: List<Type>
    ): Flow<List<CurrencyModel>> =
        currencyDao.getCurrencies(searchKeyWords, types)
            .map { it -> it.map { it.toModel() } }

    override suspend fun clearLocal() {
        currencyDao.clear()
    }

    override suspend fun createLocalSet(): Unit = withContext(coDispatcherProvider.io) {
        awaitAll(
            async { getCurrencyList("currencies_cypto.json", type = Type.CRYPTO) },
            async { getCurrencyList("currencies_fiat.json", type = Type.FIAT) }
        ).also { (crypto, fiat) ->
            currencyDao.insertAll(crypto + fiat)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun getCurrencyList(
        fileName: String,
        type: Type,
    ): List<CurrencyEntity> {
        val inputStream = context.assets.open(fileName)
        return Json.decodeFromStream<List<CurrencyJsonModel>>(inputStream).map {
            it.toEntity(type)
        }
    }

}

fun CurrencyJsonModel.toEntity(type: Type): CurrencyEntity = CurrencyEntity(
    id = id,
    name = name,
    symbol = symbol,
    type = type
)

fun CurrencyEntity.toModel(): CurrencyModel = CurrencyModel(
    id = id,
    name = name,
    symbol = symbol,
    type = type
)
