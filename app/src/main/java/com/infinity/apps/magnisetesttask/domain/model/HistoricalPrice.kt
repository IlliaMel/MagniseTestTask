package com.infinity.apps.magnisetesttask.domain.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class HistoricalPrice(
    @Json(name = "t") val time: String,
    @Json(name = "o") val open: Double,
    @Json(name = "h") val high: Double,
    @Json(name = "l") val low: Double,
    @Json(name = "c") val close: Double,
    @Json(name = "v") val volume: Int
)