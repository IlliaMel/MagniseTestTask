package com.infinity.apps.magnisetesttask.domain.model.instrument

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarketPrice(
    @Json(name = "instrumentId") val instrumentId: String,
    @Json(name = "last") val last: LastPriceData
)

@JsonClass(generateAdapter = true)
data class LastPriceData(
    @Json(name = "timestamp") val timestamp: String,
    @Json(name = "price") val price: Double
)