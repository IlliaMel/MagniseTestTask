package com.infinity.apps.magnisetesttask.presentation.navigation.screens

import android.view.LayoutInflater
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.instrument_data_card.InstrumentDataCard
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.instrument_history_card.InstrumentHistoryCard
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.instrument_realtimedata_card.InstrumentRealTimeDataCard
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.InstrumentInfoViewModel


@Composable
fun InstrumentInfoScreen(innerPadding: PaddingValues, viewModel: InstrumentInfoViewModel = hiltViewModel()) {

    val filterState by viewModel.filterState.collectAsState()

    val instrumentDataState by viewModel.instrumentDataState.collectAsState()
    val instrumentHistoryState by viewModel.instrumentHistoryState.collectAsState()
    val instrumentRealTimeDataState by viewModel.instrumentRealTimeDataState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            InstrumentDataCard(
                filterState = filterState,
                dataState = instrumentDataState,
                onChangeFilter = viewModel::onChangeFilter,
                onInstrumentChanged = viewModel::onInstrumentChanged
            )
        }

        item {
            InstrumentRealTimeDataCard(
                filterState = filterState,
                instrumentDataState = instrumentDataState,
                instrumentRealTimeDataState = instrumentRealTimeDataState,
                onSubscribe = viewModel::onSubscribe
            )
        }

        item {
            InstrumentHistoryCard(instrumentHistoryState = instrumentHistoryState)
        }
    }
}
