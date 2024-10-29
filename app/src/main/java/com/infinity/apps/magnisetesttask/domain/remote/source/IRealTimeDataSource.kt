package com.infinity.apps.magnisetesttask.domain.remote.source

import com.infinity.apps.magnisetesttask.domain.model.instrument.response.MarketPrice
import kotlinx.coroutines.flow.Flow

interface IRealTimeDataSource {

    suspend fun connectToSocket()

    suspend fun sendMassage(subscribe : Boolean, instrumentId : String, provider : String)

    suspend fun disconnectSocket()

    suspend fun observePriceUpdates(): Flow<MarketPrice>

}


