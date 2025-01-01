package com.example.kanjimemorized.ui.screens.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
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
            .background(color = MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(insets = WindowInsets.statusBars)
            .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = { Text("Statistics") },
                actions = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "info"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                )
            )
        },
        bottomBar = {
            bottomNavBar()
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Box(
//                modifier = Modifier
//                    .clickable {
//                        navController.navigateUp()
//                    }
//            ) {
//                Text(
//                    text = "Statistics",
//                    modifier = Modifier
//                        .align(alignment = Alignment.Center),
//                    fontSize = 50.sp
//                )
//            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .width(width = 350.dp)
                            .padding(2.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(all = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                            Box(
                                modifier = Modifier
                                    .padding(all = 10.dp)
                            ) {
                                Text(
                                    text = "Unlocked Kanji",
                                    fontSize = 30.sp
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .padding(all = 10.dp)
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                fontSize = 35.sp
                                            )
                                        ) {
                                            append(text = "${statisticsState.unlocked}")
                                        }
                                        append(text = " / 2136 ")
                                    },
                                    modifier = Modifier
                                        .align(alignment = Alignment.Center),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 28.sp
                                )
                            }

                        }
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
                            .width(width = 350.dp)
                            .padding(2.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(all = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Text(
                                text = "Kanji Count",
                                fontSize = 30.sp
                            )
                            PieChart(
                                modifier = Modifier
                                    .size(250.dp),
                                inputMap = statisticsState.kanjiCountMap,
                                colorMap = mapOf("Unknown" to MaterialTheme.colorScheme.tertiary, "Known" to MaterialTheme.colorScheme.secondary, "Mastered" to MaterialTheme.colorScheme.primary)
                            )
                            Box(
                                Modifier
                                    .width(width = 300.dp)
                            ) {
                                val brush = Brush.horizontalGradient(
                                    colorStops = arrayOf(0.2f to MaterialTheme.colorScheme.tertiary, 0.3f to MaterialTheme.colorScheme.secondary, 0.7f to MaterialTheme.colorScheme.secondary, 0.8f to MaterialTheme.colorScheme.primary),
                                    startX = with(LocalDensity.current) { 50.dp.toPx() },
                                    endX = with(LocalDensity.current) { 250.dp.toPx() }
                                )
                                Canvas(
                                    modifier = Modifier
                                        .size(width = 300.dp, height = 15.dp)
                                    ,
                                    onDraw = {
                                        drawLine(
                                            brush = brush,
                                            start = Offset(x = 0f, y = 15f),
                                            end = Offset(x = 300.dp.toPx(), y = 15f),
                                            strokeWidth = 30f
                                        )
                                    }
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .width(width = 300.dp),
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
                            .width(width = 350.dp)
                            .padding(2.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(all = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Text(
                                text = "Review Forecast",
                                fontSize = 30.sp
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
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
                                if (statisticsState.dayForecastsMap.isEmpty()) {
                                    Text(
                                        text = "No Data",
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontSize = 14.sp,
                                        fontStyle = FontStyle.Italic
                                    )
                                } else {
                                    BarGraph(
                                        modifier = Modifier.size(250.dp),
                                        inputMap = statisticsState.dayForecastsMap,
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
    StatisticsScreen(navController = rememberNavController(), bottomNavBar = {}, statisticsState = StatisticsState(unlocked = 824, kanjiCountMap = mapOf("Unknown" to 1612, "Known" to 412, "Mastered" to 112), dayForecastsMap = mutableMapOf(1 to 4, 2 to 5, 3 to 2, 5 to 1)), onStatisticsEvent = {})
}