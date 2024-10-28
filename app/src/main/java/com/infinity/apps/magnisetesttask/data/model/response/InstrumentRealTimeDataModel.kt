package com.infinity.apps.magnisetesttask.data.model.response

import com.infinity.apps.magnisetesttask.domain.model.instrument.response.MarketPrice
import java.text.SimpleDateFormat
import java.util.*

data class InstrumentRealTimeDataModel(
    val instrumentSymbol: String,
    val price: Double,
    val change : Double,
    val formattedTimestamp: String
) {
    companion object {
        private const val INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        private const val OUTPUT_DATE_FORMAT = "MM-dd'T'HH"

        fun fromMarketPrice(price: Double, change : Double, instrumentSymbol : String, timestamp : String): InstrumentRealTimeDataModel {
            val inputDateFormat = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.getDefault())
            val outputDateFormat = SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.getDefault())

            val formattedTimestamp = try {
                val date = inputDateFormat.parse(timestamp) ?: ""
                outputDateFormat.format(date)
            } catch (e: Exception) {
                "Invalid Date"
            }

            return InstrumentRealTimeDataModel(
                instrumentSymbol = instrumentSymbol,
                price = price,
                change = change,
                formattedTimestamp = formattedTimestamp
            )
        }
    }
}

