package com.cryptoassignment.data.repo.currency

import android.content.Context
import com.cryptoassignment.base.TestCoDispatcherProvider
import com.cryptoassignment.local.currency.CurrencyDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(value = TestInstance.Lifecycle.PER_METHOD)
class CurrencyRepoImplTest {

    private val context = mockk<Context>(relaxed = true)
    private val currencyDao = mockk<CurrencyDao>()
    private val coDispatcherProvider = TestCoDispatcherProvider()

    private val currencyRepo = CurrencyRepoImpl(
        context = context,
        currencyDao = currencyDao,
        coDispatcherProvider = coDispatcherProvider
    )
    
    @Test
    fun ` clear is called`() = runTest {
        coEvery { currencyDao.clear() } returns Unit
        currencyRepo.clearLocal()
        coVerify(exactly = 1) {
            currencyDao.clear()
        }
    }
}