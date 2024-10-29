package com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.instrument_data_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.infinity.apps.magnisetesttask.data.model.response.InstrumentDataModel
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.core.LoadingComponent
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.core.TextComponent
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state.FilterState
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state.InstrumentDataState


@Composable
fun InstrumentDataCard(filterState : FilterState , dataState: InstrumentDataState, onChangeFilter : (String) -> Unit, onInstrumentChanged : (InstrumentDataModel) -> Unit ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextComponent(text = "Instrument Data")

            FilterListComponent(
                filter = filterState.filter,
                filtersList = filterState.filtersList,
                onChangeFilter = onChangeFilter
            )

            dataState.data?.let {
                FilteredInstrumentsListComponent(
                    data = dataState.data,
                    filter = filterState.filter,
                    currentInstrumentModel = dataState.currentInstrumentModel,
                    onInstrumentChanged = onInstrumentChanged
                )
            } ?: run {
                LoadingComponent(scale = 0.8f)
            }
        }
    }
}