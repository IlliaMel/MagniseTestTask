package com.infinity.apps.magnisetesttask.domain.model.instrument.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarketPrice(
    @Json(name = "instrumentId") val instrumentId: String,
    @Json(name = "quote") val quote: QuoteData,
)

@JsonClass(generateAdapter = true)
data class QuoteData(
    @Json(name = "last") val last: PriceData
)

@JsonClass(generateAdapter = true)
data class PriceData(
    @Json(name = "timestamp") val timestamp: String,
    @Json(name = "price") val price: Double
)