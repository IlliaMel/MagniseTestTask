package com.infinity.apps.magnisetesttask.presentation.activity

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.infinity.apps.magnisetesttask.presentation.navigation.Navigation
import com.infinity.apps.magnisetesttask.ui.theme.MagniseTestTaskTheme
import dagger.hilt.android.AndroidEntryPoint

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.compose.ui.viewinterop.AndroidView
import com.infinity.apps.magnisetesttask.R

import com.scichart.charting.visuals.SciChartSurface
import com.scitrader.finance.SciFinanceChart
import com.scitrader.finance.data.DefaultCandleDataProvider
import com.scitrader.finance.data.ICandleDataProvider
import com.scitrader.finance.pane.PaneId
import com.scitrader.finance.study.studies.PriceSeriesStudy
import com.scitrader.finance.study.studies.RSIStudy



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    fun initChartApi () {
        SciChartSurface.setRuntimeLicenseKey("bSXIJKwQj6Z2swrWzo9ep654+Bo6nhxKcijYRSKIorf65dXsvMEDnQQAv5AJ3poe05UNOb9akKyEFZZH2Ys8GhfeUIMKe0TkQuI8NxIRPimnaFR2oLlX6Iryic8P46z5GLZz768o56F7udMmEPEpcBI5h7yNeh1VrQGyt1BvihMwZkCC2A0wFLN/0b04PIp91Vg5Ll6JoW4bTbVZXfO2IPQWS+vHqk5a8nw8WwHLiJfx0WOhaRcsIrOKXQ2tyiKIyzc7PRzXajDMVpoiNYVCzUW73C86LCFOu2a2NMUB+oWUxwmm9lTbsUy8LOxK6dgv9UY8XuYXT8IQfwls+QGVw469MPTbwmkHjU5Cx05f21GSlJMTynG+3HuQeu6Q1Ek3yQgGpJo4cEUxxGSa6pjQ5NUUCkhU5PmIri6rrlwrs/n6cowvDC01ZRWH/8ez+uu6FAfbBhk5YyAiaUw6Z/hbAW05mY9scIok/N9Smspl1GCsJ/EkXZy0eVe1nzKcn4LehP/l1oWOWEduodf0+vWif82+vCMf+3O6oO+EDUZv/8dlTy1Bg0VekPQcQ3IRwMrizvYejTiWn+w2Zmg=")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initChartApi ()
        setContent {
            MagniseTestTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(innerPadding = innerPadding)
                }
            }
        }
    }
}
