package com.infinity.apps.magnisetesttask.data.remote.repository

import com.infinity.apps.magnisetesttask.domain.model.instrument.HistoricalPrice
import com.infinity.apps.magnisetesttask.domain.model.instrument.InstrumentData
import com.infinity.apps.magnisetesttask.domain.model.instrument.MarketPrice
import com.infinity.apps.magnisetesttask.domain.model.core.TimeRange
import kotlinx.coroutines.flow.Flow

interface IMarketRepository {

    suspend fun getAllInstruments (): List<InstrumentData>

    fun observeRealTimePrices(): Flow<MarketPrice>

    suspend fun getHistoricalPrices(assetId: String, timeRange: TimeRange): List<HistoricalPrice>

}