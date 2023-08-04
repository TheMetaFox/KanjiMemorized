package com.example.kanjimemorized.ui.screens.ideogram

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.theme.spacing
import kotlinx.coroutines.delay

@Composable
fun IdeogramScreen(
    modifier: Modifier,
    navController: NavHostController,
    ideogramState: IdeogramState,
    onIdeogramEvent: (IdeogramEvent) -> Unit
) {
    if (ideogramState.isAddingIdeogram) {
        AddIdeogramDialog(state = ideogramState, onEvent = onIdeogramEvent)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small
            ),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onIdeogramEvent(IdeogramEvent.ShowDialog)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Ideogram"
                )
            }
        },
    ) { contentPadding ->
        Column(
            modifier = modifier
                .padding(contentPadding),
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
                                selected = mutableStateOf(ideogramState.sortType == sortType),
                                onIdeogramEvent = onIdeogramEvent
                            )
                        }
                    }
                }
                items(ideogramState.ideograms) { ideogram ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(0.5f)
                        ) {
                            Text(
                                text = ideogram.unicode.toString(),
                                fontSize = 20.sp
                            )
                            Text(
                                text = ideogram.meanings.toString().replace("[", "").replace("]",""),
                                fontSize = 12.sp
                            )
                        }
                        CircularProgressBar(
                            modifier = Modifier,
                            percentage = 0.8f,
                            number = 80,
                            fontSize = 16.sp,
                            radius = 26.dp,
                            strokeWidth = 4.dp,
                        )
                        IconButton(
                            onClick = {
                                onIdeogramEvent(IdeogramEvent.DeleteIdeogram(ideogram))
                            },
                            modifier = Modifier
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
}

@Composable
fun SortOption(
    sortType: SortType,
    selected: MutableState<Boolean>,
    onIdeogramEvent: (IdeogramEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onIdeogramEvent(IdeogramEvent.SortIdeograms(sortType))
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(
                    selected = selected.value,
            onClick = { onIdeogramEvent(IdeogramEvent.SortIdeograms(sortType)) }
        )

        Text(
            text = sortType.name
        )
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 8.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
    modifier: Modifier
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val currentPercentage = animateFloatAsState(
        targetValue = if(animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        ),
        label = "progressBar"
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(radius*2f)
    ) {
        Canvas(
            modifier = Modifier
                .size(radius*2f),
        ) {
            drawArc(
                color = color,
                -90f,
                360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (currentPercentage.value + number).toInt().toString(),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IdeogramScreenPreview() {
    IdeogramScreen(Modifier, rememberNavController(), IdeogramState()) { }
}