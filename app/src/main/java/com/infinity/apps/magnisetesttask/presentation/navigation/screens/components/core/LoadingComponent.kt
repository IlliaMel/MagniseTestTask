package com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.infinity.apps.magnisetesttask.R

@Composable
fun LoadingComponent (scale : Float = 1.2f) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))

    LottieAnimation(
        modifier = Modifier
            .scale(scale),
        speed = 1f,
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
}