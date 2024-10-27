package com.infinity.apps.magnisetesttask.domain.local.repository

import com.infinity.apps.magnisetesttask.domain.model.instrument.response.InstrumentData

interface IInstrumentDataCacheRepository {

    suspend fun getCachedInstrumentData(): List<InstrumentData>

    suspend fun setCachedInstrumentData(data: List<InstrumentData>)

}