package com.infinity.apps.magnisetesttask.data.api

import androidx.annotation.Keep
import com.infinity.apps.magnisetesttask.data.remote.interceptor.Authenticated
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.HistoricalPriceResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

@Keep
interface HistoricalApi {

    @Authenticated
    @GET("api/bars/v1/bars/count-back")
    suspend fun getData(@QueryMap(encoded = true) params: Map<String, String>): HistoricalPriceResponse

}