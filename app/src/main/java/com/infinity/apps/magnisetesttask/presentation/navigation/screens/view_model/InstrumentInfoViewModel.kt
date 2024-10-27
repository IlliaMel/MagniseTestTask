package com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model

import android.R.attr.password
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinity.apps.magnisetesttask.domain.model.core.Response
import com.infinity.apps.magnisetesttask.domain.model.instrument.param.HistoricalQueryParams
import com.infinity.apps.magnisetesttask.domain.remote.repository.IAuthRepository
import com.infinity.apps.magnisetesttask.domain.remote.source.IHistoricalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstrumentInfoViewModel @Inject constructor(
    private val authRepository : IAuthRepository,
    private val historicalDataSource : IHistoricalDataSource
) : ViewModel () {

    private suspend fun login() {
        val result = authRepository.getAuthResponse(
            username = "r_test@fintatech.com",
            password = "kisfiz-vUnvy9-sopnyv"
        )
        when (result) {
            is Response.Success -> {
                val authData = result.data
            }

            else -> {

            }
        }
    }

    private suspend fun getHistoryData() {
        val result = historicalDataSource.fetchHistoricalPrices(
            historicalQueryParams = HistoricalQueryParams (
                provider = "oanda", instrumentId = "ad9e5345-4c3b-41fc-9437-1d253f62db52"
            )
        )
        when (result) {
            is Response.Success -> {
                val historicalData = result.data
            }

            else -> {

            }
        }
    }

    /*private suspend fun getHistoryData() {
        val result = historicalDataSource.fetchHistoricalPrices(
            historicalQueryParams = HistoricalQueryParams (
                provider = "oanda", instrumentId = "ad9e5345-4c3b-41fc-9437-1d253f62db52"
            )
        )
        when (result) {
            is Response.Success -> {
                val historicalData = result.data
            }

            else -> {

            }
        }
    }*/

    init {
        viewModelScope.launch(Dispatchers.IO) { getHistoryData() }
    }

}