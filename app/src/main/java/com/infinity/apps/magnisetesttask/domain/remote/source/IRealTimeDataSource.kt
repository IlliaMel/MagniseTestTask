package com.infinity.apps.magnisetesttask.domain.remote.source

import com.infinity.apps.magnisetesttask.domain.model.MarketPrice
import kotlinx.coroutines.flow.Flow

interface IRealTimeDataSource {

    fun connectToSocket()

    fun disconnectSocket()

    fun observePriceUpdates(): Flow<MarketPrice>

}