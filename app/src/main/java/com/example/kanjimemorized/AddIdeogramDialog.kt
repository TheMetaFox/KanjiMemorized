
package com.example.kanjimemorized

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIdeogramDialog(
    state: IdeogramState,
    onEvent: (IdeogramEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(IdeogramEvent.HideDialog) },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add Ideogram",
                fontSize = 20.sp
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.id,
                    onValueChange = {
                        onEvent(IdeogramEvent.SetId(Integer.parseInt(it)))
                    },
                    placeholder = {
                        Text(text = "Id")
                    }
                )
                TextField(
                    value = state.meanings,
                    onValueChange = {
                        onEvent(IdeogramEvent.SetMeanings(it.split(",")))
                    },
                    placeholder = {
                        Text(text = "Meanings")
                    }
                )
                TextField(
                    value = state.strokes,
                    onValueChange = {
                        onEvent(IdeogramEvent.SetStrokes(Integer.parseInt(it)))
                    },
                    placeholder = {
                        Text(text = "Strokes")
                    }
                )
            }
            Button(
                onClick = {
                    onEvent(IdeogramEvent.SaveIdeogram)
                },
                ) {
                Text(text = "Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddIdeogramDialogPreview() {
    AddIdeogramDialog(state = IdeogramState(), onEvent = { IdeogramEvent.ShowDialog })
}