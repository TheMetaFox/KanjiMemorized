package com.example.kanjimemorized.ui.screens.statistics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
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
            Box(
                modifier = Modifier
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
                Canvas(
                    modifier = Modifier
                ) {
                    listOf(statisticsState.unknown, statisticsState.known, statisticsState.mastered).forEach {
                        scale(1f) {
                            drawArc(
                                color = Color.Companion.Cyan,
                                startAngle = 0f,
                                sweepAngle = 360f * it / (statisticsState.unknown + statisticsState.known + statisticsState.mastered),
                                useCenter = true,
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StatisticsScreenPreview() {
    StatisticsScreen(navController = rememberNavController(), bottomNavBar = {}, statisticsState = StatisticsState(), onStatisticsEvent = {})
}