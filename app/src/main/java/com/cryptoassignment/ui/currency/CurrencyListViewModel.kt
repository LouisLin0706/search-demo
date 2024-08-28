package com.cryptoassignment.ui.currency

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cryptoassignment.data.model.CurrencyModel
import com.cryptoassignment.data.repo.currency.CurrencyRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
class CurrencyListViewModel @Inject constructor(
    private val currencyRepo: CurrencyRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    data class CurrencyListState(
        val search: String = "",
        val isSearchResultEmpty: Boolean = false,
        val isLoading: Boolean = false,
        val results: ImmutableList<CurrencyUIModel> = mutableListOf<CurrencyUIModel>().toImmutableList()
    )

    private val params = savedStateHandle.get<CurrencyListFragment.Params>("params")
    val searchText = MutableStateFlow("")

    @VisibleForTesting
    val _type = MutableStateFlow(
        params?.types ?: emptyList()
    )


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val currencyList = combine(
        searchText,
        _type
    ) { search, type ->
        search to type
    }
        .onEach {
            _isLoading.value = true
        }
        .debounce(300)
        .flatMapLatest { (searchText, type) ->
            currencyRepo.getCurrencyList(searchText, type)
                .map { it ->
                    it.map { it.toUIModel() }
                }.onEach {
                    _isLoading.value = false
                }
        }


    private val _isLoading = MutableStateFlow(false)

    val isSearchResultEmpty = combine(currencyList, searchText) { list, search ->
        list.isEmpty() && search.isNotEmpty()
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )


    val state: Flow<CurrencyListState> = combine(
        currencyList,
        isSearchResultEmpty,
        searchText,
        _isLoading,
    ) { list, isSearchResultEmpty, search, isLoading ->
        CurrencyListState(
            search = search,
            isSearchResultEmpty = isSearchResultEmpty,
            isLoading = isLoading,
            results = list.toMutableList().toImmutableList()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CurrencyListState()
    )

    fun search(newText: String) {
        searchText.tryEmit(newText)
    }
}

fun CurrencyModel.toUIModel() = CurrencyUIModel(
    id = id,
    name = name,
    symbol = symbol,
    iconText = symbol.first().toString(),
)