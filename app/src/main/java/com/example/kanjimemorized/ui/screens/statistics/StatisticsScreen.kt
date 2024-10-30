package com.example.kanjimemorized.ui.screens.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.screens.library.LibraryEvent
import com.example.kanjimemorized.ui.screens.library.SortOption
import com.example.kanjimemorized.ui.screens.library.SortType
import com.example.kanjimemorized.ui.theme.spacing

@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomNavBar: @Composable () -> Unit,
    statisticsState: StatisticsState,
    onStatisticsEvent: (StatisticsEvent) -> Unit
) {
    onStatisticsEvent(StatisticsEvent.LoadStatisticsData)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = MaterialTheme.spacing.small),
        bottomBar = {
            bottomNavBar()
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.Home.route)
                    }
            ) {
                Text(
                    text = "Statistics",
                    modifier = Modifier
                        .align(alignment = Alignment.Center),
                    fontSize = 50.sp
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                item {
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Unlocked Kanji: " + statisticsState.unlocked,
                            modifier = Modifier
                                .align(alignment = Alignment.Center),
                            fontSize = 28.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Unknown Kanji: " + statisticsState.unknown,
                            modifier = Modifier
                                .align(alignment = Alignment.Center),
                            fontSize = 20.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Known Kanji: " + statisticsState.known,
                            modifier = Modifier
                                .align(alignment = Alignment.Center),
                            fontSize = 20.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Mastered Kanji: " + statisticsState.mastered,
                            modifier = Modifier
                                .align(alignment = Alignment.Center),
                            fontSize = 20.sp
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier
                            .size(width = 300.dp, height = 300.dp),
                    ) {
                        PieChart(
                            modifier = Modifier
                                .align(alignment = Alignment.Center),
                            input = listOf(
                                PieChartInput(
                                    value = (statisticsState.unknown).toFloat(),
                                    color = MaterialTheme.colorScheme.primary,
                                    label = "Unknown",
                                    isSelected = false
                                ),
                                PieChartInput(
                                    value = statisticsState.known.toFloat(),
                                    color = MaterialTheme.colorScheme.secondary,
                                    label = "Known",
                                    isSelected = false
                                ),
                                PieChartInput(
                                    value = statisticsState.mastered.toFloat(),
                                    color = MaterialTheme.colorScheme.tertiary,
                                    label = "Mastered",
                                    isSelected = false
                                )
                            )
                        )
                    }
                }
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            BarGraphSpan.entries.forEach { barGraphSpan ->
                                BarGraphSpanOption(
                                    barGraphSpan = barGraphSpan,
                                    selected = mutableStateOf(statisticsState.barGraphSpan == barGraphSpan),
                                    onStatisticsEvent = onStatisticsEvent
                                )
                            }
                        }
                        Box(
                            modifier = modifier
                                .size(width = 300.dp, height = 300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            BarGraph(
                                inputMap = statisticsState.dayForecastsMap,
                                width = 300.dp.value,
                                height = 300.dp.value,
                                barGraphSpan = statisticsState.barGraphSpan
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BarGraphSpanOption(
    barGraphSpan: BarGraphSpan,
    selected: MutableState<Boolean>,
    onStatisticsEvent: (StatisticsEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onStatisticsEvent(StatisticsEvent.SetBarGraphSpan(barGraphSpan))
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = barGraphSpan.name,
            fontSize = 12.sp
        )
        RadioButton(
            selected = selected.value,
            onClick = { onStatisticsEvent(StatisticsEvent.SetBarGraphSpan(barGraphSpan)) },
            modifier = Modifier.size(25.dp)
        )
    }

}



@Preview(showBackground = true)
@Composable
fun StatisticsScreenPreview() {
    StatisticsScreen(navController = rememberNavController(), bottomNavBar = {}, statisticsState = StatisticsState(dayForecastsMap = mutableMapOf(1 to 4, 2 to 5, 3 to 2, 5 to 1)), onStatisticsEvent = {})
}