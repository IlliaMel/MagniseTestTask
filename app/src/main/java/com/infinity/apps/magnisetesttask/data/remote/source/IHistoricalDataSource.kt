package com.infinity.apps.magnisetesttask.data.remote.source

import com.infinity.apps.magnisetesttask.domain.model.instrument.HistoricalPrice
import com.infinity.apps.magnisetesttask.domain.model.core.TimeRange

interface IHistoricalDataSource {

    suspend fun fetchHistoricalPrices(assetId: String, timeRange: TimeRange): List<HistoricalPrice>

}