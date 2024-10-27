package com.infinity.apps.magnisetesttask.data.remote.repository

import com.infinity.apps.magnisetesttask.domain.model.instrument.response.HistoricalPrice
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.InstrumentData
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.MarketPrice
import kotlinx.coroutines.flow.Flow

interface IMarketRepository {



    fun observeRealTimePrices(): Flow<MarketPrice>

    suspend fun getHistoricalPrices(): List<HistoricalPrice>

}