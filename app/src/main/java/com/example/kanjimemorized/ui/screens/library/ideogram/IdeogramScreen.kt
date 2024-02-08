package com.example.kanjimemorized.ui.screens.library.ideogram

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.screens.library.CircularProgressBar
import com.example.kanjimemorized.ui.screens.library.LibraryEvent
import com.example.kanjimemorized.ui.theme.spacing

@Composable
fun IdeogramScreen(
    modifier: Modifier,
    navController: NavController,
    ideogramState: IdeogramState,
    onIdeogramEvent: (IdeogramEvent) -> Unit
) {
    Scaffold (
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = MaterialTheme.spacing.medium,
                top = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium
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
                        navController.navigate(Screen.Library.route)
                    }
                    .padding(bottom = 5.dp),
            ) {
                Text(
                    text = "Ideogram",
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
                    Text(
                        text = ideogramState.ideogram?.unicode.toString(),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 100.sp,
                        lineHeight = 50.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Column() {
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
                                text = ideogramState.ideogram?.retention.toString(),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 34.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                    }
                    Column() {
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Coercivity:",
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = ideogramState.ideogram?.coercivity.toString(),
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
                            text = ideogramState.ideogram?.meanings.toString().replace("[", "").replace("]",""),
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
                            text = ideogramState.ideogram?.strokes.toString(),
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
            Column {
                Box(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                ) {
                    Text(
                        text = "Decompositions:",
                        modifier = Modifier
                            .align(alignment = Alignment.Center),

                    fontSize = 34.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    ideogramState.decompositions?.forEach { ideogram ->
                        /*
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                            ) {
                                Text(
                                    text = ideogram.unicode.toString(),
                                    modifier = Modifier.align(Alignment.Center),
                                    fontSize = 32.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            HorizontalProgressBar(
                                percentage = .8f,
                                width = 100.dp,
                                height = 20.dp,
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                            Box(
                                modifier = Modifier
                            ) {
                                Text(
                                    text = ideogram.meanings.toString(),
                                    modifier = Modifier.align(Alignment.Center),
                                    fontSize = 32.sp,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }*/
                        Row(
                            modifier = Modifier
                                .height(75.dp)
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    onIdeogramEvent(IdeogramEvent.DisplayIdeogramInfo(ideogram))
                                    navController.navigate(Screen.Ideogram.route)
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
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
                                percentage = 0.8f,
                                number = 80,
                                fontSize = 16.sp,
                                radius = 26.dp,
                                strokeWidth = 4.dp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HorizontalProgressBar(
    percentage: Float,
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier,
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
        label = "horizontalProgressBar"
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(
                width = width,
                height = height
            )
    ) {
        Canvas(
            modifier = modifier
                .size(
                    width = width,
                    height = height
                )
        ) {
            drawLine(
                color = color,
                start = Offset(0f, height.toPx()/2),
                end = Offset(width.toPx()*currentPercentage.value, height.toPx()/2),
                strokeWidth = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun IdeogramScreenPreview() {
    IdeogramScreen(modifier = Modifier, navController = rememberNavController(), ideogramState = IdeogramState(), onIdeogramEvent = {})
}