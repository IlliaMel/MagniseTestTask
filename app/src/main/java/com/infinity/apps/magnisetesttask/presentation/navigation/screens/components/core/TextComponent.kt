package com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextComponent (text : String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(modifier = Modifier.padding(8.dp).align(Alignment.Center), text = text, style = MaterialTheme.typography.bodyMedium)
    }
}