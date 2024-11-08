package com.example.kanjimemorized.ui.screens.library

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiEvent
import com.example.kanjimemorized.ui.theme.spacing
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalDateTime.parse
import java.time.format.DateTimeFormatter
import kotlin.math.exp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun LibraryScreen(
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navController: NavHostController,
    bottomNavBar: @Composable () -> Unit,
    libraryState: LibraryState,
    onLibraryEvent: (LibraryEvent) -> Unit,
    onKanjiEvent: (KanjiEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = spacing.small),
        bottomBar = {
            bottomNavBar()
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .padding(contentPadding)
                .padding(
                    start = spacing.small,
                    top = spacing.small,
                    end = spacing.small
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigateUp()
                    },
            ) {
                Text(
                    text = "Library",
                    modifier = Modifier
                        .align(alignment = Alignment.Center),
                    fontSize = 50.sp
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SortType.entries.forEach { sortType ->
                            SortOption(
                                sortType = sortType,
                                selected = mutableStateOf(libraryState.sortType == sortType),
                                onLibraryEvent = onLibraryEvent
                            )
                        }
                    }
                }
                Log.i("LibraryScreen.kt", "Items")
                if (libraryState.kanji.isEmpty()) {
                    Log.i("LibraryScreen.kt", "libraryState.kanji is an empty list")
                } else {
                    Log.i("LibraryScreen.kt", libraryState.kanji.toString())
                }
                if (libraryState.meaning.isEmpty()) {
                    Log.i("LibraryScreen.kt", "libraryState.meaning is an empty list")
                } else {
                    Log.i("LibraryScreen.kt", libraryState.meaning.toString())
                }
                if (libraryState.date.isEmpty()) {
                    Log.i("LibraryScreen.kt", "libraryState.date is an empty list")
                } else {
                    Log.i("LibraryScreen.kt", libraryState.date.toString())
                }
                items(libraryState.kanji.size) {
                    Log.i("LibraryScreen.kt", libraryState.meaning[it])
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .clickable {
                                onKanjiEvent(KanjiEvent.DisplayKanjiInfo(libraryState.kanji[it]))
                                navController.navigate(Screen.Kanji.route)
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(0.5f)
                        ) {
                            with(sharedTransitionScope) {
                                Text(
                                    text = libraryState.kanji[it].unicode.toString(),
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .sharedBounds(
                                            rememberSharedContentState(key = libraryState.kanji[it].unicode.toString()),
                                            animatedVisibilityScope = animatedVisibilityScope,
                                            boundsTransform = {_, _ ->
                                                tween(durationMillis = 1000)
                                            },
                                            zIndexInOverlay = 1f
                                        )
                                )

                            }
                            Text(
                                text = libraryState.meaning[it].replace("[", "").replace("]","").replace(",", ", "),
                                fontSize = 12.sp
                            )
                        }
                        CircularProgressBar(
                            percentage = if (libraryState.kanji[it].durability == 0f || libraryState.date[it] == null) 0f else (exp(-(((Duration.between(
                                parse(libraryState.date[it], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                LocalDateTime.now()
                            ).toMinutes()).toDouble()/1440) / libraryState.kanji[it].durability)).toFloat()),//(1/i++).toFloat(),//retention,
                            number = libraryState.kanji[it].durability.toInt(),
                            fontSize = 16.sp,
                            radius = 26.dp,
                            strokeWidth = 4.dp,
                        )
                    }
                }
                item {
                    Button(
                        onClick = {
                            onLibraryEvent(LibraryEvent.ResetKanji)
                        },
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary,
                            disabledContainerColor = MaterialTheme.colorScheme.secondary,
                            disabledContentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text(
                            text = "Reset Kanji Data"
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
    onLibraryEvent: (LibraryEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onLibraryEvent(LibraryEvent.SortKanji(sortType))
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(
                    selected = selected.value,
            onClick = { onLibraryEvent(LibraryEvent.SortKanji(sortType)) }
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
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 8.dp,
    animationDuration: Int = 1000,
    animationDelay: Int = 0,
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
        label = "circularProgressBar"
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
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
        Text(
            text = number.toString(),
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LibraryScreenPreview() {
//    Animate
//    LibraryScreen(Modifier, AnimatedContentScope, rememberNavController(), { }, LibraryState(), { }, { })
//}