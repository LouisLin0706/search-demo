package com.cryptoassignment.local.currency

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "currencies", primaryKeys = ["id"])
data class CurrencyEntity(
    @SerialName("id")
    @ColumnInfo(name = "id")
    val id: String,

    @SerialName("name")
    @ColumnInfo(name = "name", index = true)
    val name: String,

    @SerialName("symbol")
    @ColumnInfo(name = "symbol")
    val symbol: String,


    @SerialName("type")
    @ColumnInfo(name = "type")
    val type: Type,
)


enum class Type {
    CRYPTO,
    FIAT
}