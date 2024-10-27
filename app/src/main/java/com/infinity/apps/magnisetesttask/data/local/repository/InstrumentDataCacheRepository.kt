package com.infinity.apps.magnisetesttask.data.local.repository

import com.infinity.apps.magnisetesttask.domain.model.instrument.InstrumentData

interface InstrumentDataCacheRepository {

    suspend fun getCachedInstrumentData(): List<InstrumentData>

    suspend fun setCachedInstrumentData(data: List<InstrumentData>)

}