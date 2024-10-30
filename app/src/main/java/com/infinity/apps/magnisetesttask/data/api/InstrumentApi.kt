package com.infinity.apps.magnisetesttask.data.api

import androidx.annotation.Keep
import com.infinity.apps.magnisetesttask.data.remote.interceptor.Authenticated
import com.infinity.apps.magnisetesttask.domain.model.instrument.response.InstrumentDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

@Keep
interface InstrumentApi {

    @Authenticated
    @GET("api/instruments/v1/instruments")
    suspend fun getData(@Query("size") size: Int): InstrumentDataResponse

}