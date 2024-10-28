package com.infinity.apps.magnisetesttask.data.model.response

import com.infinity.apps.magnisetesttask.domain.model.instrument.response.HistoricalPrice
import java.text.SimpleDateFormat
import java.util.*

data class HistoricalPriceModel(
    val date: String,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double
) {
    companion object {
        private const val INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        private const val OUTPUT_DATE_FORMAT = "MM-dd'T'HH"

        fun fromHistoricalPrices(historicalPrices: List<HistoricalPrice>): List<HistoricalPriceModel> {
            return historicalPrices.map { historicalPrice ->
                val formattedDate = try {
                    val inputDateFormat = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.getDefault())
                    val outputDateFormat = SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.getDefault())
                    val date = inputDateFormat.parse(historicalPrice.timestamp) ?: ""
                    outputDateFormat.format(date)
                } catch (e: Exception) {
                    "Invalid Date"
                }

                HistoricalPriceModel(
                    date = formattedDate,
                    open = historicalPrice.open,
                    high = historicalPrice.high,
                    low = historicalPrice.low,
                    close = historicalPrice.close
                )
            }
        }
    }
}