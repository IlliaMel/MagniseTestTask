package com.infinity.apps.magnisetesttask.data.remote.source

import com.infinity.apps.magnisetesttask.data.api.HistoricalApi
import com.infinity.apps.magnisetesttask.data.api.InstrumentApi
import com.infinity.apps.magnisetesttask.domain.model.core.Response
import com.infinity.apps.magnisetesttask.domain.model.instrument.param.HistoricalQueryParams
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.HistoricalPriceResponse
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.InstrumentDataResponse
import com.infinity.apps.magnisetesttask.domain.remote.source.IHistoricalDataSource
import com.infinity.apps.magnisetesttask.domain.remote.source.IInstrumentsDataSource
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InstrumentsDataSourceImpl @Inject constructor(
    private val instrumentApi : InstrumentApi
) : IInstrumentsDataSource {

    override suspend fun getAllInstruments(size: Int): Response<InstrumentDataResponse> {
        return try {
            val response = instrumentApi.getData(size = size)
            Response.Success(response)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                Response.UnauthorizedError(message = "${e.message()}")
            } else {
                Response.DefaultError(message = "${e.code()}: ${e.message()}")
            }
        } catch (e: IOException) {
            Response.DefaultError(message = e.message.toString())
        }
    }
}