package com.example.kanjimemorized.ui.screens.statistics

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.BottomNavBar
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    statisticsState: StatisticsState,
    onStatisticsEvent: (StatisticsEvent) -> Unit
) {
    onStatisticsEvent(StatisticsEvent.LoadStatisticsData)
    Scaffold(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(insets = WindowInsets.statusBars)
            .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = { Text("Statistics") },
                actions = {
                    var showDropDownMenu by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = { showDropDownMenu = true }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "more vertical"
                        )
                    }
                    val localUriHandler: UriHandler = LocalUriHandler.current
                    DropdownMenu(
                        expanded = showDropDownMenu,
                        onDismissRequest = { showDropDownMenu = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text("App Guide") },
                            onClick = {
                                showDropDownMenu = false
                                localUriHandler.openUri("https://github.com/TheMetaFox/KanjiMemorized?tab=readme-ov-file#app-guide")
                            },
                            leadingIcon = { Icon(Icons.Outlined.Info, "info") }
                        )
                        DropdownMenuItem(
                            text = { Text("Feedback") },
                            onClick = {
                                showDropDownMenu = false
                                localUriHandler.openUri("https://docs.google.com/forms/d/e/1FAIpQLScQzby5vRCzXCFfAlnzWv6iUmuMwS1J6PlYcG7HzOxW8hTwnw/viewform?usp=sf_link")
                            },
                            leadingIcon = { Icon(Icons.Outlined.Feedback, "feedback") }
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
            BottomNavBar(selected = "Statistics", navController = navController)
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        UnlockedKanjiCard(statisticsState = statisticsState)
                        KanjiCountCard(statisticsState = statisticsState)
                        ReviewForecastCard(statisticsState = statisticsState, onStatisticsEvent = onStatisticsEvent)
                    }
                }
            }
        }
    }
}

@Composable
fun UnlockedKanjiCard(
    statisticsState: StatisticsState
) {
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
}

@Composable
fun KanjiCountCard(
    statisticsState: StatisticsState
) {
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
                val progressColorMap: Map<String, Color> = mapOf(
                    "Unknown" to MaterialTheme.colorScheme.tertiary,
                    "Known" to MaterialTheme.colorScheme.secondary,
                    "Mastered" to MaterialTheme.colorScheme.primary
                )
                progressColorMap.forEach { (progress, color) ->
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
                                    color = color
                                )
                            }
                        )
                        Text(
                            text = progress,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "${statisticsState.kanjiCountMap[progress]}"
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ReviewForecastCard(
    statisticsState: StatisticsState,
    onStatisticsEvent: (StatisticsEvent) -> Unit
) {
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
                modifier = Modifier
                    .size(width = 300.dp, height = 300.dp),
                contentAlignment = Alignment.Center
            ) {
                if (statisticsState.dayForecastsMap.isEmpty()) {
                    Text(
                        text = "No Data",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
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
            text = barGraphSpan.toString(),
            fontSize = 12.sp,
            letterSpacing = 0.2f.sp
        )
        RadioButton(
            selected = selected.value,
            onClick = { onStatisticsEvent(StatisticsEvent.SetBarGraphSpan(barGraphSpan)) },
            modifier = Modifier.size(25.dp)
        )
    }

}



val statisticsState = StatisticsState(
    unlocked = 824,
    kanjiCountMap = mapOf("Unknown" to 1612, "Known" to 412, "Mastered" to 112),
    dayForecastsMap = mutableMapOf(1 to 4, 2 to 5, 3 to 2, 5 to 1)
)

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StatisticsScreenPreview_DarkLight() {
    KanjiMemorizedTheme {
        StatisticsScreen(navController = rememberNavController(), statisticsState = statisticsState, onStatisticsEvent = {})
    }
}
@Preview(name = "85%", fontScale = 0.85f)
@Preview(name = "150%", fontScale = 1.5f)
@Preview(name = "200%", fontScale = 2f)
@Composable
fun StatisticsScreenPreview_FontScale() {
    KanjiMemorizedTheme {
        StatisticsScreen(navController = rememberNavController(), statisticsState = statisticsState, onStatisticsEvent = {})
    }
}

@Preview(name = "Light", group = "Cards")
@Preview(name = "Dark", group = "Cards", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UnlockedKanjiCardPreview_DarkLight() {
    KanjiMemorizedTheme {
        UnlockedKanjiCard(statisticsState = statisticsState)
    }
}
@Preview(name = "85%", group = "Cards", fontScale = 0.85f)
@Preview(name = "150%", group = "Cards", fontScale = 1.5f)
@Preview(name = "200%", group = "Cards", fontScale = 2f)
@Composable
fun UnlockedKanjiCardPreview_FontScale() {
    KanjiMemorizedTheme {
        UnlockedKanjiCard(statisticsState = statisticsState)
    }
}
@Preview(name = "Light", group = "Cards")
@Preview(name = "Dark", group = "Cards", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun KanjiCountCardPreview_DarkLight() {
    KanjiMemorizedTheme {
        KanjiCountCard(statisticsState = statisticsState)
    }
}
@Preview(name = "85%", group = "Cards", fontScale = 0.85f)
@Preview(name = "150%", group = "Cards", fontScale = 1.5f)
@Preview(name = "200%", group = "Cards", fontScale = 2f)
@Composable
fun KanjiCountCardPreview_FontScale() {
    KanjiMemorizedTheme {
        KanjiCountCard(statisticsState = statisticsState)
    }
}
@Preview(name = "Light", group = "Cards")
@Preview(name = "Dark", group = "Cards", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ReviewForecastCardPreview_DarkLight() {
    KanjiMemorizedTheme {
        ReviewForecastCard(statisticsState = statisticsState, onStatisticsEvent = { })
    }
}
@Preview(name = "85%", group = "Cards", fontScale = 0.85f)
@Preview(name = "150%", group = "Cards", fontScale = 1.5f)
@Preview(name = "200%", group = "Cards", fontScale = 2f)
@Composable
fun ReviewForecastCardPreview_FontScale() {
    KanjiMemorizedTheme {
        ReviewForecastCard(statisticsState = statisticsState, onStatisticsEvent = { })
    }
}