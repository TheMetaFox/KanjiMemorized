package com.example.kanjimemorized.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.AddIdeogramDialog
import com.example.kanjimemorized.data.IdeogramEvent
import com.example.kanjimemorized.data.IdeogramState
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.data.SortType

@Composable
fun IdeogramScreen(
    navController: NavHostController,
    state: IdeogramState,
    onEvent: (IdeogramEvent) -> Unit
) {
    if (state.isAddingIdeogram) {
        AddIdeogramDialog(state = state, onEvent = onEvent)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Box(
            modifier = Modifier
                .clickable {
                    navController.navigate(Screen.Home.route)
                },
        ) {
            Text(
                text = "Ideogram",
                modifier = Modifier
                    .align(alignment = Alignment.Center),
                fontSize = 50.sp
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    SortType.values().forEach { sortType ->
                        SortOption(
                            sortType = sortType,
                            selected = mutableStateOf(state.sortType == sortType),
                            onEvent = onEvent
                        )
                    }
                }
            }
            items(state.ideograms) { ideogram ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = ideogram.unicode.toString(),
                            fontSize = 20.sp
                        )
                        Text(
                            text = ideogram.meanings.toString(),
                            fontSize = 12.sp
                        )
                    }
                    IconButton(
                        onClick = {
                            onEvent(IdeogramEvent.DeleteIdeogram(ideogram))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Ideogram"
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun SortOption(
    sortType: SortType,
    selected: MutableState<Boolean>,
    onEvent: (IdeogramEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onEvent(IdeogramEvent.SortIdeograms(sortType))
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(
            selected = selected.value,
            onClick = { onEvent(IdeogramEvent.SortIdeograms(sortType)) }
        )
        Text(
            text = sortType.name
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IdeogramScreenPreview() {
    IdeogramScreen(rememberNavController(), IdeogramState()) { }
}