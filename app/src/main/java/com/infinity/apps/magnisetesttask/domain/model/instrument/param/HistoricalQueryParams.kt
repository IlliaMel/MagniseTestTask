package com.infinity.apps.magnisetesttask.domain.model.instrument.param

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class HistoricalQueryParams(
    @Json(name = "interval") val interval: String = "1",
    @Json(name = "periodicity") val periodicity: String = "hour",
    @Json(name = "instrumentId") val instrumentId: String,
    @Json(name = "provider") val provider: String,
    @Json(name = "barsCount") val barsCount: String = "720"
)

