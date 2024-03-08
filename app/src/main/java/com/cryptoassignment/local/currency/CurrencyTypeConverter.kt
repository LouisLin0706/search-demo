package com.cryptoassignment.local.currency

import androidx.room.TypeConverter

class CurrencyTypeConverter {
    @TypeConverter
    fun fromPriority(type: Type): String {
        return type.name
    }

    @TypeConverter
    fun toPriority(type: String): Type {
        return Type.valueOf(type)
    }
}