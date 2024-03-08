package com.cryptoassignment.data.model

import com.cryptoassignment.local.currency.Type

data class CurrencyModel(
    val id: String,
    val name: String,
    val symbol: String,
    val type: Type
)