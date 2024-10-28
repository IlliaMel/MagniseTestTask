package com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state

import com.infinity.apps.magnisetesttask.data.model.response.HistoricalPriceModel
import com.infinity.apps.magnisetesttask.data.model.response.InstrumentDataModel


data class InstrumentDataState(
    val data: List<InstrumentDataModel>? = null,
    val currentInstrumentModel : InstrumentDataModel? = null,
    val filter: String = "All-Providers",
    val providersList : List<String> = listOf<String>(
        "active-tick",
        "alpaca",
        "cryptoquote",
        "dxfeed",
        "oanda",
        "simulation"
    )
)