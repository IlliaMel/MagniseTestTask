package com.infinity.apps.magnisetesttask.data.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "instrument_data")
data class InstrumentDataEntity(
    @PrimaryKey val id: String,  // Use `id` as primary key
    val symbol: String,
    val kind: String,
    val exchange: String?,
    val description: String,
    val tickSize: Double,
    val currency: String,
    val baseCurrency: String?,
    val mappings: Map<String, Any?>?
)