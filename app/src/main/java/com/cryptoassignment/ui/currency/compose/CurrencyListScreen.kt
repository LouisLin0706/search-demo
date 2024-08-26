package com.cryptoassignment.ui.currency.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cryptoassignment.R
import com.cryptoassignment.ui.currency.CurrencyListViewModel
import com.cryptoassignment.ui.currency.CurrencyUIModel


@Composable
internal fun CurrencyListScreen(
    viewModel: CurrencyListViewModel = hiltViewModel()
) {

    val searchQuery by viewModel.searchText.collectAsState("")
    val items by viewModel.currencyList.collectAsState(emptyList())
    val isSearchResultEmpty by viewModel.isSearchResultEmpty.collectAsState(false)
    Content(
        searchQuery = searchQuery,
        items = items,
        isSearchResultEmpty = isSearchResultEmpty,
        search = { viewModel.search(it) }
    )
}


@Preview(showBackground = true, name = "Currency List Screen")
@Composable
private fun Content(
    searchQuery: String = "test",
    items: List<CurrencyUIModel> = listOf(
        CurrencyUIModel(
            id = "1",
            name = "Bitcoin",
            symbol = "BTC",
            iconText = "B"
        ),
        CurrencyUIModel(
            id = "2",
            name = "Ethereum",
            symbol = "ETH",
            iconText = "E"
        ),
        CurrencyUIModel(
            id = "3",
            name = "Ripple",
            symbol = "XRP",
            iconText = "R"
        )
    ),
    isSearchResultEmpty: Boolean = false,
    search: (String) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        SearchInputField(searchQuery) {
            search(it)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isSearchResultEmpty -> Text("Search nothing")
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items) { item ->
                        CurrencyItem(item)
                    }
                }
            }
        }
    }
}


@Composable
fun SearchInputField(
    query: String,
    onValueChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                1.dp,
                Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White)
    ) {
        IconButton(
            onClick = {
                if (isFocused) {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                } else {

                }
            },
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = if (!isFocused) Icons.Default.Search else Icons.Default.ArrowBack,
                contentDescription = if (!isFocused) "Search" else "Back",
                tint = Color.Gray
            )
        }
        TextField(
            value = query,
            onValueChange = { newText ->
                onValueChange(newText)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused // Update focus state
                }
                .align(Alignment.Center),
            textStyle = TextStyle(
                fontSize = 20.sp,
                color = Color.Black
            ),

            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text("Search...") }
        )

        if (query.isNotEmpty()) {
            IconButton(
                onClick = { onValueChange("") },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear text",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
internal fun CurrencyItem(currencyUIModel: CurrencyUIModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currencyUIModel.iconText,
                color = Color.Gray,
                fontSize = 18.sp
            )
        }
        Column(
            modifier = Modifier.weight(1f).padding(start = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = currencyUIModel.name,
            )
        }
        Text(
            text = currencyUIModel.symbol,
            color = Color.Gray
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_vector_arrow_right),
            contentDescription = "Arrow",
            tint = Color.Gray
        )
    }
}



