package com.infinity.apps.magnisetesttask.domain.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class InstrumentData(
    @Json(name = "id") val id: String,
    @Json(name = "symbol") val symbol: String,
    @Json(name = "kind") val kind: String,
    @Json(name = "exchange") val exchange: String,
    @Json(name = "description") val description: String,
    @Json(name = "tickSize") val tickSize: Double,
    @Json(name = "currency") val currency: String,
    @Json(name = "baseCurrency") val baseCurrency: String
)