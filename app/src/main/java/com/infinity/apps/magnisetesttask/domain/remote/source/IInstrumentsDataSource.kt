package com.infinity.apps.magnisetesttask.domain.remote.source

import com.infinity.apps.magnisetesttask.domain.model.core.Response
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.InstrumentDataResponse

interface IInstrumentsDataSource {

    suspend fun getAllInstruments(size: Int): Response<InstrumentDataResponse>

}