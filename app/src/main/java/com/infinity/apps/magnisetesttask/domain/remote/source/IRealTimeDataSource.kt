package com.infinity.apps.magnisetesttask.domain.remote.source

import com.infinity.apps.magnisetesttask.domain.model.instrument.response.MarketPrice
import kotlinx.coroutines.flow.Flow

interface IRealTimeDataSource {

    suspend fun connectToSocket(instrumentId : String)

    suspend fun disconnectSocket()

    suspend fun observePriceUpdates(): Flow<MarketPrice>

}


