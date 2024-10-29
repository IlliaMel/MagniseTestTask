package com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.instrument_data_card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterListComponent (filter : String?, filtersList : List<String>, onChangeFilter : (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.TopStart).clickable(onClick = { expanded = true })) {

        Box (modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(8.dp)), contentAlignment = Alignment.CenterStart) {
            Text(modifier = Modifier.padding(4.dp), text = "Filter by Provider: ${filter ?: ""}", style = MaterialTheme.typography.bodySmall)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            filtersList.forEach { provider ->
                DropdownMenuItem(
                    text = { Text(text = provider, style = MaterialTheme.typography.bodySmall) },
                    onClick = { onChangeFilter(provider); expanded = false }
                )
            }
        }
    }
}
