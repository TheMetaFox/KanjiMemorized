package com.example.kanjimemorized.ui.screens.library.kanji

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.screens.library.CircularProgressBar
import com.example.kanjimemorized.ui.theme.spacing
import java.time.Duration
import java.time.LocalDateTime
import java.util.Locale
import kotlin.math.exp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun KanjiScreen(
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navController: NavController,
    kanjiState: KanjiState,
    onKanjiEvent: (KanjiEvent) -> Unit
) {
    if (kanjiState.isShowingReviewData) {
        KanjiReviewDataDialog(onKanjiEvent = onKanjiEvent, kanjiState = kanjiState)
    }
//    Log.i("KanjiScreen.kt", "Setting Selected to 0...")
//    val selected = remember {
//        mutableIntStateOf(-1)
//    }
    Scaffold (
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = spacing.medium,
                top = spacing.medium,
                end = spacing.medium
            )
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        navController.popBackStack(Screen.Library.route, false)
                    }
                    .padding(bottom = 5.dp),
            ) {
                Text(
                    text = "Kanji",
                    modifier = Modifier
                        .align(alignment = Alignment.Center),
                    fontSize = 50.sp
                )
            }

            Row(
                modifier = Modifier
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .weight(1f),
                ) {
                    with(sharedTransitionScope) {
//                        Log.i("KanjiScreen.kt", "Shared Kanji Key: " + kanjiState.kanji.unicode.toString())
//                        Log.i("KanjiScreen.kt", "Shared Kanji Key: ${selected.intValue}")
                        Text(
                            text = kanjiState.kanji.unicode.toString(),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .sharedBounds(
                                    rememberSharedContentState(key = kanjiState.kanji.unicode.toString()),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 1000)
                                    },
                                    zIndexInOverlay = 1f
                                )
//                                .sharedBounds(
//                                    rememberSharedContentState(key = selected.intValue),
//                                    animatedVisibilityScope = animatedVisibilityScope,
//                                    boundsTransform = { _, _ ->
//                                        tween(durationMillis = 1000)
//                                    },
//                                    zIndexInOverlay = 1f
//                                )
                                ,
                            fontSize = 100.sp,
                            lineHeight = 50.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Retention:",
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = String.format(Locale.ENGLISH ,"%.2f", kanjiState.retention),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 34.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                    }
                    Column {
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Durability:",
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = String.format(Locale.ENGLISH,"%.0f", kanjiState.kanji.durability
                                ),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 34.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .width(600.dp)
                    .padding(bottom = 5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Meanings: ",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = kanjiState.meaning.toString().replace("[", "").replace("]",""),
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Strokes: ",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = kanjiState.kanji.strokes.toString(),
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .height(height = 400.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                ) {
                    Text(
                        text = "Components:",
                        modifier = Modifier
                            .align(alignment = Alignment.Center),

                    fontSize = 34.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Log.i("KanjiScreen.kt", kanjiState.components.toString()+kanjiState.componentsLatestDates.toString())
                    items(kanjiState.components.size) {
                        Row(
                            modifier = Modifier
                                .height(75.dp)
                                .padding(horizontal = 16.dp)
                                .clickable {
//                                    selected.intValue = it
                                    onKanjiEvent(KanjiEvent.DisplayKanjiInfo(kanjiState.components[it]))
                                    //navController.navigate(Screen.Kanji.route)
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                            ) {
                                with(sharedTransitionScope) {
//                                    Log.i("KanjiScreen.kt", "Shared Component Key: " + kanjiState.components[it].unicode.toString())
//                                    Log.i("KanjiScreen.kt", "Shared Component Key: ${selected.intValue}")
                                    Text(
                                        text = kanjiState.components[it].unicode.toString(),
                                        fontSize = 20.sp,
                                        modifier = Modifier
                                            .sharedBounds(
                                                rememberSharedContentState(key = kanjiState.components[it].unicode.toString()),
                                                animatedVisibilityScope = animatedVisibilityScope,
                                                boundsTransform = { _, _ ->
                                                    tween(durationMillis = 1000)
                                                },
                                                zIndexInOverlay = 1f
                                            )
//                                            .sharedBounds(
//                                                rememberSharedContentState(key = it),
//                                                animatedVisibilityScope = animatedVisibilityScope,
//                                                boundsTransform = { _, _ ->
//                                                    tween(durationMillis = 1000)
//                                                },
//                                                zIndexInOverlay = 1f
//                                            )
                                    )
                                }
                                Text(
                                    text = kanjiState.componentMeaning[it].toString().replace("[", "").replace("]",""),
                                    fontSize = 12.sp
                                )
                            }
                            CircularProgressBar(
                                percentage = if (kanjiState.components[it].durability == 0f || kanjiState.componentsLatestDates[it] == null) 0f else (exp(-(((Duration.between(
                                    kanjiState.componentsLatestDates[it],
                                    LocalDateTime.now()
                                ).toMinutes()).toDouble()/1440) / kanjiState.components[it].durability)).toFloat()),
                                number = kanjiState.components[it].durability,
                                fontSize = 16.sp,
                                radius = 26.dp,
                                strokeWidth = 4.dp,
                            )
                        }
                    }
                }
            }
            Button(
                onClick = {
                    onKanjiEvent(KanjiEvent.ShowKanjiReviewData)
                },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary,
                    disabledContentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text(
                    text = "Review Data"
                )
            }
        }
    }
}

//@Composable
//fun HorizontalProgressBar(
//    percentage: Float,
//    width: Dp,
//    height: Dp,
//    modifier: Modifier = Modifier,
//    color: Color = MaterialTheme.colorScheme.primary,
//    strokeWidth: Dp = 8.dp,
//    animationDuration: Int = 1000,
//    animationDelay: Int = 0,
//    ) {
//    var animationPlayed by remember {
//        mutableStateOf(false)
//    }
//    val currentPercentage = animateFloatAsState(
//        targetValue = if(animationPlayed) percentage else 0f,
//        animationSpec = tween(
//            durationMillis = animationDuration,
//            delayMillis = animationDelay
//        ),
//        label = "horizontalProgressBar"
//    )
//    LaunchedEffect(key1 = true) {
//        animationPlayed = true
//    }
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = modifier
//            .size(
//                width = width,
//                height = height
//            )
//    ) {
//        Canvas(
//            modifier = modifier
//                .size(
//                    width = width,
//                    height = height
//                )
//        ) {
//            drawLine(
//                color = color,
//                start = Offset(0f, height.toPx()/2),
//                end = Offset(width.toPx()*currentPercentage.value, height.toPx()/2),
//                strokeWidth = strokeWidth.toPx(),
//                cap = StrokeCap.Round
//            )
//        }
//    }
//}


//@OptIn(ExperimentalSharedTransitionApi::class)
//@Preview(showBackground = true)
//@Composable
//fun KanjiScreenPreview() {
//    AnimatedContent { targetState ->
//        KanjiScreen(modifier = Modifier, this , navController = rememberNavController(), kanjiState = KanjiState(), onKanjiEvent = {})
//    }
//}