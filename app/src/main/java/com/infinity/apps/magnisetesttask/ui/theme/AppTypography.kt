package com.infinity.apps.magnisetesttask.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.infinity.apps.magnisetesttask.R

val AppTypography = Typography(
    bodySmall = TextStyle(
        fontFamily = FontFamily (Font(R.font.ibm_plex_mono_light)),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        textAlign = TextAlign.Center
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily (Font(R.font.ibm_plex_mono_light)),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        textAlign = TextAlign.Center
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily (Font(R.font.ibm_plex_mono_light)),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        textAlign = TextAlign.Center
    )
)

