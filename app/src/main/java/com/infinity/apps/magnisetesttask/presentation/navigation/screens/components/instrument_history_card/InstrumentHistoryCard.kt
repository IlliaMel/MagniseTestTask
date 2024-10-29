package com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.instrument_history_card

import android.R.attr.data
import android.provider.SyncStateContract.Helpers.update
import android.view.LayoutInflater
import android.view.View
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
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.compose.LottieAnimatable
import com.infinity.apps.magnisetesttask.R
import com.infinity.apps.magnisetesttask.data.model.response.HistoricalPriceModel
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.core.LoadingComponent
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.core.TextComponent
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model.state.InstrumentHistoryState
import com.scichart.charting.visuals.SciChartSurface
import com.scitrader.finance.SciFinanceChart
import com.scitrader.finance.data.DefaultCandleDataProvider
import com.scitrader.finance.data.ICandleDataProvider
import com.scitrader.finance.pane.PaneId
import com.scitrader.finance.study.studies.PriceSeriesStudy



@Composable
fun InstrumentHistoryCard (instrumentHistoryState : InstrumentHistoryState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextComponent(text = "Last 30 day history")

            instrumentHistoryState.data?.let { data ->
                AndroidView(
                    modifier = Modifier,
                    factory = { context ->
                        LayoutInflater.from(context).inflate(R.layout.chart_layout, null)
                    },
                    update = { view ->
                        val chart = view.findViewById<SciFinanceChart>(R.id.financeChart)
                        chart.studies.clear()

                        val candleDataProvider = DefaultCandleDataProvider()
                        chart.candleDataProvider = candleDataProvider

                        fillDataProvider(candleDataProvider, data)

                        chart.studies.add(PriceSeriesStudy(PaneId.DEFAULT_PANE))
                        chart.isCursorEnabled = true
                        view
                    }
                )
            } ?: run {
                LoadingComponent(scale = 1.2f)
            }
        }
    }
}

private fun fillDataProvider(dataProvider: ICandleDataProvider, candles: List<HistoricalPriceModel>) {
    for (candlestick in candles) {
        dataProvider.xValues.addTime(candlestick.date)
        dataProvider.openValues.add(candlestick.open)
        dataProvider.highValues.add(candlestick.high)
        dataProvider.lowValues.add(candlestick.low)
        dataProvider.closeValues.add(candlestick.close)
        dataProvider.volumeValues.add(candlestick.volume.toDouble())
    }
}