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
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DemoControlPanelViewModel @Inject constructor(
    private val currencyRepo: CurrencyRepo,
    private val coDispatcherProvider: CoDispatcherProvider
) : ViewModel() {

    data class DemoControlPaneUiState(
        val userMessage: String? = null,
        val navigation: CurrencyListFragment.Params? = null
    )

    private val _uiState = MutableStateFlow(DemoControlPaneUiState())
    val uiState: StateFlow<DemoControlPaneUiState> = _uiState

    private val _toast = MutableStateFlow<String?>(null)
    val toast: SharedFlow<String?> = _toast

    private val eHandler = CoroutineExceptionHandler { _, e ->
        _toast.tryEmit("Error: ${e.message}")
        _uiState.update { it.copy(userMessage = "Error: ${e.message}") }
    }

    private val _navigation = PublishSharedFlow<CurrencyListFragment.Params>()
    val navigation: Flow<CurrencyListFragment.Params> = _navigation


    fun clearDBClicked() {
        viewModelScope.launch(coDispatcherProvider.io + eHandler) {
            currencyRepo.clearLocal()
            _uiState.update { it.copy(userMessage = "DB Cleared!") }
        }
    }

    fun createDBClicked() {
        viewModelScope.launch(coDispatcherProvider.io + eHandler) {
            currencyRepo.createLocalSet()
            _uiState.update { it.copy(userMessage = "DB Created!") }
        }
    }

    fun showCurrencyList(vararg type: Type) {
        _navigation.tryEmit(CurrencyListFragment.Params(types = type.toList()))
    }

    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }
}