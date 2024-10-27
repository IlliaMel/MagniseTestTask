package com.infinity.apps.magnisetesttask.data.remote.source

import com.infinity.apps.magnisetesttask.domain.model.instrument.MarketPrice
import kotlinx.coroutines.flow.Flow

interface IRealTimeDataSource {

    fun connectToSocket()

    fun disconnectSocket()

    fun observePriceUpdates(): Flow<MarketPrice>

}