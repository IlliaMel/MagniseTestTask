package com.infinity.apps.magnisetesttask.data.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class Converters @Inject constructor(private val moshi: Moshi) {

    private val mapType = Types.newParameterizedType(Map::class.java, String::class.java, Any::class.javaObjectType)
    private val jsonAdapter = moshi.adapter<Map<String, Any?>>(mapType)

    @TypeConverter
    fun fromMap(value: Map<String, Any?>?): String? {
        return value?.let { jsonAdapter.toJson(it) }
    }

    @TypeConverter
    fun toMap(value: String?): Map<String, Any?>? {
        return value?.let { jsonAdapter.fromJson(it) }
    }
}