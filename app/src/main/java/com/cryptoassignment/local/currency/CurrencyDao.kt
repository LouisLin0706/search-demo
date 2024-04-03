package com.cryptoassignment.local.currency

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query(
        "SELECT * FROM currencies " +
                "WHERE type IN (:types) " +
                "AND (name LIKE '% %' || :searchKeyWords || '%' " +
                "OR symbol LIKE :searchKeyWords || '%' " +
                "OR name LIKE :searchKeyWords || '%')" +
                "ORDER BY name ASC"
    )
    fun getCurrencies(
        searchKeyWords: String = "",
        types: List<Type> = listOf(Type.CRYPTO, Type.FIAT)
    ): Flow<List<CurrencyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<CurrencyEntity>)

    @Query("DELETE FROM currencies")
    suspend fun clear()
}