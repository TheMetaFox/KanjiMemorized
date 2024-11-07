package com.example.kanjimemorized.ui.screens.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.Screen
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
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                item {
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Unlocked Kanji: " + statisticsState.unlocked,
                            modifier = Modifier
                                .align(alignment = Alignment.Center),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 28.sp
                        )
                    }
//                    Box(
//                        modifier = Modifier
//                    ) {
//                        Text(
//                            text = "Unknown Kanji: " + statisticsState.kanjiCountMap["Unknown"],
//                            modifier = Modifier
//                                .align(alignment = Alignment.Center),
//                            color = MaterialTheme.colorScheme.onBackground,
//                            fontSize = 20.sp
//                        )
//                    }
//                    Box(
//                        modifier = Modifier
//                    ) {
//                        Text(
//                            text = "Known Kanji: " + statisticsState.kanjiCountMap["Known"],
//                            modifier = Modifier
//                                .align(alignment = Alignment.Center),
//                            color = MaterialTheme.colorScheme.onBackground,
//                            fontSize = 20.sp
//                        )
//                    }
//                    Box(
//                        modifier = Modifier
//                    ) {
//                        Text(
//                            text = "Mastered Kanji: " + statisticsState.kanjiCountMap["Mastered"],
//                            modifier = Modifier
//                                .align(alignment = Alignment.Center),
//                            color = MaterialTheme.colorScheme.onBackground,
//                            fontSize = 20.sp
//                        )
//                    }
                }
                item {
                    Card(
                        modifier = Modifier
                            .size(width = 350.dp, height = 400.dp)
                            .padding(2.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            PieChart(
                                modifier = Modifier
                                    .size(300.dp),
                                inputMap = statisticsState.kanjiCountMap,
                                colorMap = mapOf("Unknown" to MaterialTheme.colorScheme.tertiary, "Known" to MaterialTheme.colorScheme.secondary, "Mastered" to MaterialTheme.colorScheme.primary)
                            )
                            val brush = Brush.horizontalGradient(
                                colorStops = arrayOf(0.2f to MaterialTheme.colorScheme.tertiary, 0.3f to MaterialTheme.colorScheme.secondary, 0.7f to MaterialTheme.colorScheme.secondary, 0.8f to MaterialTheme.colorScheme.primary),
                                startX = with(LocalDensity.current) { 50.dp.toPx() },
                                endX = with(LocalDensity.current) { 250.dp.toPx() }
                                )
                            Box(
                                Modifier
                                    .width(width = 300.dp)
                            ) {
                                Canvas(
                                    modifier = Modifier
                                        .size(width = 300.dp, height = 20.dp),
                                    onDraw = {
                                        drawLine(
                                            brush = brush,
                                            start = Offset(x = 0f, y = 20f),
                                            end = Offset(x = 300.dp.toPx(), y = 20f),
                                            strokeWidth = 30f
                                        )
                                    }
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .size(width = 300.dp, height = 75.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val colorUnknown = MaterialTheme.colorScheme.tertiary
                                val colorKnown = MaterialTheme.colorScheme.secondary
                                val colorMastered = MaterialTheme.colorScheme.primary
                                Column(
                                    modifier = Modifier,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ){
                                    Canvas(
                                        modifier = Modifier
                                            .size(size = 15.dp)
                                            .padding(all = 2.dp),
                                        onDraw = {
                                            drawCircle(
                                                color = colorUnknown
                                            )
                                        }
                                    )
                                    Text(
                                        text = "Unknown"
                                    )
                                    Text(
                                        text = "${statisticsState.kanjiCountMap["Unknown"]}"
                                    )
                                }
                                Column(
                                    modifier = Modifier,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ){
                                    Canvas(
                                        modifier = Modifier
                                            .size(15.dp)
                                            .padding(all = 2.dp),
                                        onDraw = {
                                            drawCircle(
                                                color = colorKnown
                                            )
                                        }
                                    )
                                    Text(
                                        text = "Known"
                                    )
                                    Text(
                                        text = "${statisticsState.kanjiCountMap["Known"]}"
                                    )

                                }
                                Column(
                                    modifier = Modifier,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ){
                                    Canvas(
                                        modifier = Modifier
                                            .size(15.dp)
                                            .padding(all = 2.dp),
                                        onDraw = {
                                            drawCircle(
                                                color = colorMastered
                                            )
                                        }
                                    )
                                    Text(
                                        text = "Mastered"
                                    )
                                    Text(
                                        text = "${statisticsState.kanjiCountMap["Mastered"]}"
                                    )

                                }
                            }
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier
                            .size(width = 350.dp, height = 400.dp)
                            .padding(2.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(all = 10.dp),
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
    StatisticsScreen(navController = rememberNavController(), bottomNavBar = {}, statisticsState = StatisticsState(kanjiCountMap = mapOf("Unknown" to 1612, "Known" to 412, "Mastered" to 112), dayForecastsMap = mutableMapOf(1 to 4, 2 to 5, 3 to 2, 5 to 1)), onStatisticsEvent = {})
}