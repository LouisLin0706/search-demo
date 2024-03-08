package com.cryptoassignment.ui.currency

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.cryptoassignment.data.model.CurrencyModel
import com.cryptoassignment.data.repo.currency.CurrencyRepo
import com.cryptoassignment.local.currency.Type
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


internal class CurrencyListViewModelTest {
    private val currencyRepo: CurrencyRepo = mockk()
    private val savedStateHandle: SavedStateHandle = SavedStateHandle().also {
        it["params"] = CurrencyListFragment.Params(
            listOf(
                Type.CRYPTO
            )
        )
    }
    private val viewModel: CurrencyListViewModel =
        CurrencyListViewModel(currencyRepo, savedStateHandle)


    @Test
    fun `should get proper types from SavedStateHandle`() = runTest {
        viewModel._type.test {
            val result = awaitItem()
            Assertions.assertEquals(listOf(Type.CRYPTO), result)
            cancelAndIgnoreRemainingEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should get first char of symbol as iconText`() {
        val currencyModel = CurrencyModel(
            id = "1",
            name = "Bitcoin",
            symbol = "BTC",
            type = Type.CRYPTO
        )
        val result = currencyModel.toUIModel()
        Assertions.assertEquals("B", result.iconText)
    }
}