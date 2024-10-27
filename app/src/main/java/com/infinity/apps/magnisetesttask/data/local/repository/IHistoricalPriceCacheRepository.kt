package com.infinity.apps.magnisetesttask.data.local.repository

import com.infinity.apps.magnisetesttask.domain.model.instrument.HistoricalPrice

interface IHistoricalPriceCacheRepository {

    suspend fun getCachedHistoricalPriceData(): List<HistoricalPrice>

    suspend fun setCachedHistoricalPriceData(data : List<HistoricalPrice>)

}