package com.infinity.apps.magnisetesttask.data.remote.source

import com.infinity.apps.magnisetesttask.data.api.InstrumentApi
import com.infinity.apps.magnisetesttask.domain.model.core.Response
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.InstrumentDataResponse
import com.infinity.apps.magnisetesttask.domain.remote.source.IInstrumentsDataSource
import retrofit2.HttpException
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
        } catch (e: Exception) {
            Response.DefaultError(message = e.message.toString())
        }
    }
}