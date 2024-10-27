package com.infinity.apps.magnisetesttask.domain.local.repository

import com.infinity.apps.magnisetesttask.domain.model.HistoricalPrice

interface IHistoricalPriceCacheRepository {

    suspend fun getCachedHistoricalPriceData(): List<HistoricalPrice>

    suspend fun setCachedHistoricalPriceData(data : List<HistoricalPrice>)

}