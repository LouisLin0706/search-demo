package com.cryptoassignment.local.localcurrency

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.cryptoassignment.TestDispatcherRule
import com.cryptoassignment.local.AppDatabase
import com.cryptoassignment.local.currency.CurrencyDao
import com.cryptoassignment.local.currency.CurrencyEntity
import com.cryptoassignment.local.currency.Type
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrencyDaoTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var database: AppDatabase
    private lateinit var currencyDao: CurrencyDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .build()

        currencyDao = database.currencyDao

    }

    @After
    fun closeDatabase() {
        database.close()
    }


    @Test
    fun query_foo_should_return_Foobar_item() = runTest {
        val testCurrencies = listOf(
            CurrencyEntity("1", "Barfoo", "ETH", Type.CRYPTO),
            CurrencyEntity("2", "Foobar", "ETC", Type.CRYPTO),
            CurrencyEntity("3", "Ethereum,", "ETN", Type.CRYPTO),
            CurrencyEntity("4", "Ethereum Classic", "BET", Type.CRYPTO),
            CurrencyEntity("5", "Tronclassic", "BET", Type.CRYPTO),
        )
        currencyDao.insertAll(testCurrencies)
        currencyDao.getCurrencies("foo").test {
            val outPut = awaitItem()
            Assert.assertTrue(
                outPut.map { it.name }.contains("Foobar") && outPut.size == 1
            )
        }
    }


    @Test
    fun query_Ethereum_should_return_Ethereum_EthereumClassic_item() = runBlocking {
        val testCurrencies = listOf(
            CurrencyEntity("1", "Barfoo", "ETH", Type.CRYPTO),
            CurrencyEntity("2", "Foobar", "ETC", Type.CRYPTO),
            CurrencyEntity("3", "Ethereum", "ETN", Type.CRYPTO),
            CurrencyEntity("4", "Ethereum Classic", "BET", Type.CRYPTO),
            CurrencyEntity("5", "Tronclassic", "BET", Type.CRYPTO),
        )
        currencyDao.insertAll(testCurrencies)
        currencyDao.getCurrencies("Ethereum").test {
            val outPut = awaitItem()
            Assert.assertTrue(
                outPut.map { it.name }.containsAll(
                    listOf("Ethereum", "Ethereum Classic")
                ) && outPut.size == 2
            )
        }
    }


    @Test
    fun query_Classic_should_return_EthereumClassic_item() = runBlocking {
        val testCurrencies = listOf(
            CurrencyEntity("1", "Barfoo", "ETH", Type.CRYPTO),
            CurrencyEntity("2", "Foobar", "ETC", Type.CRYPTO),
            CurrencyEntity("3", "Ethereum", "ETN", Type.CRYPTO),
            CurrencyEntity("4", "Ethereum Classic", "BET", Type.CRYPTO),
            CurrencyEntity("5", "Tronclassic", "BET", Type.CRYPTO),
        )
        currencyDao.insertAll(testCurrencies)
        currencyDao.getCurrencies("classic").test {
            val outPut = awaitItem()
            Assert.assertTrue(
                outPut.map { it.name }.contains("Ethereum Classic")
                        && outPut.size == 1
            )
        }
    }

    @Test
    fun query_ET_should_return_ETH_ETC_ETN_item() = runBlocking {
        val testCurrencies = listOf(
            CurrencyEntity("1", "Barfoo", "ETH", Type.CRYPTO),
            CurrencyEntity("2", "Foobar", "ETC", Type.CRYPTO),
            CurrencyEntity("3", "Ethereum", "ETN", Type.CRYPTO),
            CurrencyEntity("4", "Tronclassic", "BET", Type.CRYPTO),
        )
        currencyDao.insertAll(testCurrencies)
        currencyDao.getCurrencies("ET").test {
            val outPut = awaitItem()
            Assert.assertTrue(
                outPut.map { it.symbol }.containsAll(
                    listOf("ETH", "ETC", "ETN")
                ) && outPut.size == 3
            )
        }
    }

    @Test
    fun query_without_conditions_should_return_all_items() = runBlocking {
        val testCurrencies = listOf(
            CurrencyEntity("1", "Barfoo", "ETH", Type.CRYPTO),
            CurrencyEntity("2", "Foobar", "ETC", Type.CRYPTO),
            CurrencyEntity("3", "Ethereum", "ETN", Type.CRYPTO),
            CurrencyEntity("4", "Ethereum Classic", "BET", Type.CRYPTO),
            CurrencyEntity("5", "Tronclassic", "BET", Type.CRYPTO),
        )
        currencyDao.insertAll(testCurrencies)
        currencyDao.getCurrencies().test {
            Assert.assertTrue(
                awaitItem().size == 5
            )
        }
    }

    @Test
    fun query_crypto_should_return_all_crypto_items() = runBlocking {
        val testCurrencies = listOf(
            CurrencyEntity("1", "Barfoo", "ETH", Type.CRYPTO),
            CurrencyEntity("2", "Foobar", "ETC", Type.CRYPTO),
            CurrencyEntity("3", "Ethereum", "ETN", Type.CRYPTO),
            CurrencyEntity("4", "Ethereum Classic", "BET", Type.CRYPTO),
            CurrencyEntity("5", "Tronclassic", "BET", Type.CRYPTO),
            CurrencyEntity("6", "US Dollar", "USD", Type.FIAT),
            CurrencyEntity("7", "Euro", "EUR", Type.FIAT),
            CurrencyEntity("8", "Japanese Yen", "JPY", Type.FIAT),
            CurrencyEntity("9", "British Pound", "GBP", Type.FIAT),
            CurrencyEntity("10", "Australian Dollar", "AUD", Type.FIAT),
        )
        currencyDao.insertAll(testCurrencies)
        currencyDao.getCurrencies(types = listOf(Type.CRYPTO)).test {
            val outPut = awaitItem()
            Assert.assertTrue(
                outPut.map { it.name }.containsAll(
                    listOf("Barfoo", "Foobar", "Ethereum", "Ethereum Classic", "Tronclassic")
                ) && outPut.size == 5
            )
        }
    }

    @Test
    fun query_fiat_should_return_all_fiat_items() = runBlocking {
        val testCurrencies = listOf(
            CurrencyEntity("1", "Barfoo", "ETH", Type.CRYPTO),
            CurrencyEntity("2", "Foobar", "ETC", Type.CRYPTO),
            CurrencyEntity("3", "Ethereum", "ETN", Type.CRYPTO),
            CurrencyEntity("4", "Ethereum Classic", "BET", Type.CRYPTO),
            CurrencyEntity("5", "Tronclassic", "BET", Type.CRYPTO),
            CurrencyEntity("6", "US Dollar", "USD", Type.FIAT),
            CurrencyEntity("7", "Euro", "EUR", Type.FIAT),
            CurrencyEntity("8", "Japanese Yen", "JPY", Type.FIAT),
            CurrencyEntity("9", "British Pound", "GBP", Type.FIAT),
            CurrencyEntity("10", "Australian Dollar", "AUD", Type.FIAT),
        )
        currencyDao.insertAll(testCurrencies)
        currencyDao.getCurrencies(types = listOf(Type.FIAT)).test {
            val outPut = awaitItem()
            Assert.assertTrue(
                outPut.map { it.name }.containsAll(
                    listOf(
                        "US Dollar",
                        "Euro",
                        "Japanese Yen",
                        "British Pound",
                        "Australian Dollar"
                    )
                ) && outPut.size == 5
            )
        }
    }
}