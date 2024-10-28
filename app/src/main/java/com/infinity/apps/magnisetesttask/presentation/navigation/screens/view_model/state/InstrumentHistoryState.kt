package com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state

import com.infinity.apps.magnisetesttask.data.model.response.HistoricalPriceModel

data class InstrumentHistoryState(
    val data: List<HistoricalPriceModel>? = null
)