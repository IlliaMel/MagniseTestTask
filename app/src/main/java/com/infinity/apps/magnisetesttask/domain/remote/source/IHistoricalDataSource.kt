package com.infinity.apps.magnisetesttask.domain.remote.source

import com.infinity.apps.magnisetesttask.domain.model.instrument.HistoricalPrice
import com.infinity.apps.magnisetesttask.domain.model.core.Response
import com.infinity.apps.magnisetesttask.domain.model.core.TimeRange

interface IHistoricalDataSource {

    suspend fun fetchHistoricalPrices(assetId: String, timeRange: TimeRange): Response<List<HistoricalPrice>>

}