package com.cryptoassignment.ui.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cryptoassignment.base.CoDispatcherProvider
import com.cryptoassignment.base.PublishSharedFlow
import com.cryptoassignment.data.repo.currency.CurrencyRepo
import com.cryptoassignment.local.currency.Type
import com.cryptoassignment.ui.currency.CurrencyListFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DemoControlPanelViewModel @Inject constructor(
    private val currencyRepo: CurrencyRepo,
    private val coDispatcherProvider: CoDispatcherProvider
) : ViewModel() {


    private val _toast = PublishSharedFlow<String>()
    val toast: SharedFlow<String> = _toast

    private val eHandler = CoroutineExceptionHandler { _, e ->
        _toast.tryEmit("Error: ${e.message}")
    }

    private val _navigation = PublishSharedFlow<CurrencyListFragment.Params>()
    val navigation: Flow<CurrencyListFragment.Params> = _navigation

    fun clearDBClicked() {
        viewModelScope.launch(coDispatcherProvider.io + eHandler) {
            currencyRepo.clearLocal()
            _toast.tryEmit("DB Cleared!")
        }
    }

    fun createDBClicked() {
        viewModelScope.launch(coDispatcherProvider.io + eHandler) {
            currencyRepo.createLocalSet()
            _toast.tryEmit("DB Created!")
        }
    }

    fun showCurrencyList(vararg type: Type) {
        _navigation.tryEmit(CurrencyListFragment.Params(types = type.toList()))
    }
}