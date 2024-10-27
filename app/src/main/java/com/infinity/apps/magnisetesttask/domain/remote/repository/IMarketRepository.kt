package com.infinity.apps.magnisetesttask.domain.remote.repository

import com.infinity.apps.magnisetesttask.domain.model.HistoricalPrice
import com.infinity.apps.magnisetesttask.domain.model.InstrumentData
import com.infinity.apps.magnisetesttask.domain.model.MarketPrice
import com.infinity.apps.magnisetesttask.domain.model.TimeRange
import kotlinx.coroutines.flow.Flow

interface IMarketRepository {

    suspend fun getAllInstruments (): List<InstrumentData>

    fun observeRealTimePrices(): Flow<MarketPrice>

    suspend fun getHistoricalPrices(assetId: String, timeRange: TimeRange): List<HistoricalPrice>

}