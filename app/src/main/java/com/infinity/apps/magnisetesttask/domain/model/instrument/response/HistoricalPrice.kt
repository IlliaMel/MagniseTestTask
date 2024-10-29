package com.infinity.apps.magnisetesttask.domain.model.instrument.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class HistoricalPriceResponse(
    @Json(name = "data") val data: List<HistoricalPrice>
) {
    fun getSortedPricesByTimestamp(): List<HistoricalPrice> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        return data.sortedBy {
            try {
                dateFormat.parse(it.timestamp)
            } catch (e: Exception) {
                Date(Long.MIN_VALUE)
            }
        }
    }
}

@JsonClass(generateAdapter = true)
data class HistoricalPrice(
    @Json(name = "t") val timestamp: String,
    @Json(name = "o") val open: Double,
    @Json(name = "h") val high: Double,
    @Json(name = "l") val low: Double,
    @Json(name = "c") val close: Double,
    @Json(name = "v") val volume: Int
)