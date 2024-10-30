package com.infinity.apps.magnisetesttask.domain.model.instrument.response

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MarketPrice(
    val type: String,
    val instrumentId: String,
    val provider: String,
    val last: PriceData
)

@Keep
@Serializable
data class PriceData(
    val timestamp: String,
    val price: Double,
    val volume: Int,
    val change: Double,
    val changePct: Double
)