package com.cryptoassignment.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CurrencyJsonModel(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("symbol")
    val symbol: String,

    @SerialName("code")
    val code: String? = null
)

