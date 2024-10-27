package com.infinity.apps.magnisetesttask.domain.remote.source

import com.infinity.apps.magnisetesttask.domain.model.HistoricalPrice
import com.infinity.apps.magnisetesttask.domain.model.TimeRange

interface IHistoricalDataSource {

    suspend fun fetchHistoricalPrices(assetId: String, timeRange: TimeRange): List<HistoricalPrice>

}