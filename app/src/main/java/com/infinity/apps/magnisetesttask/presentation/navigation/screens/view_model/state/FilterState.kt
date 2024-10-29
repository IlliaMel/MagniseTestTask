package com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state

data class FilterState(
    val filter: String? = "simulation",
    val filtersList : List<String> = listOf<String>(
        "simulation",
        "alpaca",
        "cryptoquote",
        "dxfeed",
        "oanda",
        "active-tick"
    )
)

