package com.infinity.apps.magnisetesttask.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.infinity.apps.magnisetesttask.presentation.navigation.screens.InstrumentInfoScreen
import kotlinx.serialization.Serializable

sealed interface NavigationState {
    @Serializable
    data object  InstrumentInfoScreen : NavigationState
}

@Composable
fun Navigation (innerPadding: PaddingValues, navController : NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = NavigationState.InstrumentInfoScreen) {
        composable <NavigationState.InstrumentInfoScreen> {
            InstrumentInfoScreen (innerPadding = innerPadding)
        }
    }
}



