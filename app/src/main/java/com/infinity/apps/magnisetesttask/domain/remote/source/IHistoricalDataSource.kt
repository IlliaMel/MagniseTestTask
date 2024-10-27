package com.infinity.apps.magnisetesttask.domain.remote.source

import com.infinity.apps.magnisetesttask.domain.model.core.Response
import com.infinity.apps.magnisetesttask.domain.model.instrument.param.HistoricalQueryParams
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.HistoricalPriceResponse

interface IHistoricalDataSource {

    suspend fun fetchHistoricalPrices(historicalQueryParams: HistoricalQueryParams): Response<HistoricalPriceResponse>

}