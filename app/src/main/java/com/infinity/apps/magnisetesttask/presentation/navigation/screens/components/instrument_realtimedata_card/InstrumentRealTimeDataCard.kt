package com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.instrument_realtimedata_card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.core.LoadingComponent
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.core.TextComponent
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state.FilterState
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state.InstrumentDataState
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state.RealTimeDataState


@Composable
fun InstrumentRealTimeDataCard(
    filterState : FilterState,
    instrumentDataState: InstrumentDataState,
    instrumentRealTimeDataState : RealTimeDataState,
    onSubscribe : (Boolean, String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextComponent(text = "Market Data")

            instrumentDataState.currentInstrumentModel?.let { instrument ->
                filterState.filter?.let {
                    Box(
                        modifier = Modifier
                            .height(40.dp).background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            ).fillMaxWidth(1f)
                            .clickable(onClick = {
                                onSubscribe(
                                    !instrumentRealTimeDataState.isSubscribe,
                                    instrument.id,
                                    filterState.filter
                                )
                            }),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (instrumentRealTimeDataState.isSubscribe == true) "Unsubscribe" else "Subscribe",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                instrumentRealTimeDataState.realTimeData?.let { data ->
                    RealTimeDataComponent(data = data)
                }?: run {
                    LoadingComponent(scale = 0.8f)
                }
            } ?: run {
                LoadingComponent(scale = 0.8f)
            }
        }
    }
}