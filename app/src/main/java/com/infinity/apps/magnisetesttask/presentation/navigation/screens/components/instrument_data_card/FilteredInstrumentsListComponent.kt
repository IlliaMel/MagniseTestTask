package com.infinity.apps.magnisetesttask.presentation.navigation.screens.components.instrument_data_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.infinity.apps.magnisetesttask.data.model.response.InstrumentDataModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteredInstrumentsListComponent (
    filter : String?,
    data : List<InstrumentDataModel>?,
    currentInstrumentModel : InstrumentDataModel?,
    onInstrumentChanged : (InstrumentDataModel) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ExposedDropdownMenuBox(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            Box(
                modifier = Modifier
                    .height(35.dp).fillMaxWidth(1f)
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentInstrumentModel?.symbol ?: "Choose Instrument",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            ExposedDropdownMenu(
                modifier = Modifier.heightIn(max = 250.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                val filteredData = data?.filter {
                    it.providerList.contains(filter.toString().lowercase())
                } ?: emptyList()

                filteredData.forEach { instrument ->
                    DropdownMenuItem(
                        text = {
                            Text(text = "${instrument.symbol} / ${instrument.kind} ${instrument.exchange?.let { if (it.isBlank()) "" else "/ $it" } ?: ""}",
                                style = MaterialTheme.typography.bodySmall)
                        },
                        onClick = {
                            onInstrumentChanged(instrument)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}