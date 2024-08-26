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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cryptoassignment.R
import com.cryptoassignment.local.currency.Type
import com.cryptoassignment.ui.demo.DemoControlPanelViewModel


@Preview
@Composable
internal fun DemoControlPanelScreen(
    navController: NavController,
    viewModel: DemoControlPanelViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val toastMessage by viewModel.toast.collectAsState(null)
    val navigation by viewModel.navigation.collectAsState(null)
    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(navigation) {
        navigation?.let {
            navController.navigate(
                R.id.action_demoPanelFragment_to_currencyListFragment2,
                args = bundleOf(
                    "params" to it
                )
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VerticalButton(text = "Clear DB") { viewModel.clearDBClicked() }
        VerticalButton(text = "Create DB") { viewModel.createDBClicked() }
        VerticalButton(text = "Show Crypto") { viewModel.showCurrencyList(Type.CRYPTO) }
        VerticalButton(text = "Show Fiat Currency") { viewModel.showCurrencyList(Type.FIAT) }
        VerticalButton(text = "Show All Currency") { viewModel.showCurrencyList(Type.CRYPTO, Type.FIAT) }
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