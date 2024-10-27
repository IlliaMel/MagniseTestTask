package com.infinity.apps.magnisetesttask.data.remote.source

import com.infinity.apps.magnisetesttask.data.api.HistoricalApi
import com.infinity.apps.magnisetesttask.domain.model.core.Response
import com.infinity.apps.magnisetesttask.domain.model.instrument.param.HistoricalQueryParams
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.HistoricalPrice
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.HistoricalPriceResponse
import com.infinity.apps.magnisetesttask.domain.remote.source.IHistoricalDataSource
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class HistoricalDataSourceImpl @Inject constructor(
    private val historicalApi : HistoricalApi,
    private val moshi: Moshi
) : IHistoricalDataSource {

    override suspend fun fetchHistoricalPrices(historicalQueryParams: HistoricalQueryParams): Response<HistoricalPriceResponse> {
        return try {
            val adapter = moshi.adapter(HistoricalQueryParams::class.java)
            val json = adapter.toJson(historicalQueryParams)
            val params: Map<String, String> =
                moshi.adapter<Map<String, String>>(Map::class.java).fromJson(json)
                    ?: throw IOException("${HistoricalQueryParams::class.java} converter problem")

            val response = historicalApi.getData(params = params)

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