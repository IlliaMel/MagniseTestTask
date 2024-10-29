package com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.instrument_realtimedata_card

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.infinity.apps.magnisetesttask.data.model.response.InstrumentRealTimeDataModel
import kotlinx.coroutines.delay

@Composable
fun RealTimeDataComponent (data : InstrumentRealTimeDataModel) {

    var targetColor by remember { mutableStateOf(Color.Transparent) }
    var lastPrice by remember { mutableStateOf(data.price) }
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 400), label = ""
    )


    LaunchedEffect(data.price) {
        targetColor = if (data.price - lastPrice < 0) {
            Color(111, 0, 0, 206)
        } else {
            Color(35, 111, 0, 206)
        }
        delay(500)
        targetColor = Color.Transparent
        lastPrice = data.price
    }


    Row(
        modifier = Modifier.fillMaxWidth().background(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(8.dp)
        ).background(color = animatedColor, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Symbol", style = MaterialTheme.typography.bodySmall)
            Text(text = data.instrumentSymbol, style = MaterialTheme.typography.bodySmall)
        }

        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Price", style = MaterialTheme.typography.bodySmall)
            Text(text = data.price.toString(), style = MaterialTheme.typography.bodySmall)
        }

        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Time", style = MaterialTheme.typography.bodySmall)
            Text(text = data.formattedTimestamp, style = MaterialTheme.typography.bodySmall)
        }

    }
}
