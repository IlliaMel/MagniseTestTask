package com.infinity.apps.magnisetesttask.data.model.response

import com.infinity.apps.magnisetesttask.domain.model.instrument.response.HistoricalPrice
import java.text.SimpleDateFormat
import java.util.*

data class HistoricalPriceModel(
    val date: Long,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Int
) {
    companion object {
        private const val INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

        fun fromHistoricalPrices(historicalPrices: List<HistoricalPrice>): List<HistoricalPriceModel> {
            return historicalPrices.map { historicalPrice ->

                val timestampInMillis = try {
                    val inputDateFormat = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.getDefault())
                    val date = inputDateFormat.parse(historicalPrice.timestamp)
                    date?.time ?: 0L
                } catch (e: Exception) {
                    0L
                }

                HistoricalPriceModel(
                    date = timestampInMillis,
                    open = historicalPrice.open,
                    high = historicalPrice.high,
                    low = historicalPrice.low,
                    close = historicalPrice.close,
                    volume = historicalPrice.volume
                )
            }
        }
    }
}