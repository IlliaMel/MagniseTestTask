package com.infinity.apps.magnisetesttask.data.local.repository

import com.infinity.apps.magnisetesttask.data.db.dao.InstrumentDataDao
import com.infinity.apps.magnisetesttask.data.model.db.InstrumentDataEntity
import com.infinity.apps.magnisetesttask.domain.local.repository.IInstrumentDataCacheRepository
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.InstrumentData
import javax.inject.Inject

class InstrumentDataCacheRepositoryImpl @Inject constructor(
    private val instrumentDataDao: InstrumentDataDao
) : IInstrumentDataCacheRepository {

    override suspend fun getCachedInstrumentData(): List<InstrumentData> {
        return instrumentDataDao.getCachedInstrumentData().map { entity ->
            InstrumentData(
                id = entity.id,
                symbol = entity.symbol,
                kind = entity.kind,
                exchange = entity.exchange,
                description = entity.description,
                tickSize = entity.tickSize,
                currency = entity.currency,
                baseCurrency = entity.baseCurrency,
                mappings = entity.mappings
            )
        }
    }

    override suspend fun setCachedInstrumentData(data: List<InstrumentData>) {
        val entityData = data.map {
            InstrumentDataEntity(
                id = it.id,
                symbol = it.symbol,
                kind = it.kind,
                exchange = it.exchange,
                description = it.description,
                tickSize = it.tickSize,
                currency = it.currency,
                baseCurrency = it.baseCurrency,
                mappings = it.mappings
            )
        }
        instrumentDataDao.setCachedInstrumentData(entityData)
    }
}