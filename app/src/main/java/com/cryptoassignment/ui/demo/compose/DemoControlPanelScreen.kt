package com.cryptoassignment.ui.demo.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.cryptoassignment.R
import com.cryptoassignment.local.currency.Type
import com.cryptoassignment.ui.demo.DemoControlPanelViewModel


@Composable
internal fun DemoControlPanelScreen(
    viewModel: DemoControlPanelViewModel = hiltViewModel(),
) {
    Content({
        viewModel.clearDBClicked()
    }, {
        viewModel.createDBClicked()
    }, {
        viewModel.showCurrencyList(Type.CRYPTO)
    }, {
        viewModel.showCurrencyList(Type.FIAT)
    }, {
        viewModel.showCurrencyList(Type.CRYPTO, Type.FIAT)
    })
}

@Preview(showBackground = true, name = "DemoControlPanelScreen Preview")
@Composable
private fun Content(
    onClearDBClicked: () -> Unit? = { null },
    onCreateDBClicked: () -> Unit? = { null },
    onShowCryptoDBClicked: () -> Unit? = { null },
    onShowFiatCurrencyClicked: () -> Unit? = { null },
    onShowAllCurrencyClicked: () -> Unit? = { null },
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VerticalButton(text = "Clear DB") { onClearDBClicked() }
        VerticalButton(text = "Create DB") { onCreateDBClicked() }
        VerticalButton(text = "Show Crypto") { onShowCryptoDBClicked() }
        VerticalButton(text = "Show Fiat Currency") { onShowFiatCurrencyClicked() }
        VerticalButton(text = "Show All Currency") { onShowAllCurrencyClicked() }
    }
}


@Composable
fun VerticalButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}