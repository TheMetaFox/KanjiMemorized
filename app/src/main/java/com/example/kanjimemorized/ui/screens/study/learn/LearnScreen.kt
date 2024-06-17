package com.example.kanjimemorized.ui.screens.study.learn

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.screens.study.learn.LearnEvent
import com.example.kanjimemorized.ui.screens.study.learn.LearnState
import com.example.kanjimemorized.ui.theme.spacing

@Composable
fun LearnScreen(
    modifier: Modifier,
    navController: NavHostController,
    learnState: LearnState,
    onLearnEvent: (LearnEvent) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(MaterialTheme.spacing.small),
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.Study.route)
                    }
            ) {
                Text(
                    text = "Learn",
                    modifier = Modifier
                        .align(alignment = Alignment.Center),
                    fontSize = 50.sp
                )
            }
            Log.i("LearnScreen.kt", "Is Review Available: ${learnState.isReviewAvailable}")
            if (learnState.isReviewAvailable) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.5f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(150.dp,100.dp)
                        ) {
                            Text(
                                text = learnState.kanji?.unicode.toString(),
                                modifier = modifier.align(Alignment.Center),
                                fontSize = 50.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        if (learnState.isAnswerShowing) {
                            Box(
                                modifier = Modifier
                                    .size(400.dp,100.dp)
                            ) {
                                Text(
                                    text = learnState.meanings.toString().replace("[", "").replace("]", ""),
                                    modifier = modifier.align(Alignment.Center),
                                    fontSize = 40.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    if (learnState.isAnswerShowing) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    onLearnEvent(LearnEvent.FlipFlashcard)
                                    onLearnEvent(LearnEvent.WrongCard)
                                    onLearnEvent(LearnEvent.GetRandomFlashcard)
                                },
                                modifier = Modifier
                                    .size(125.dp,50.dp),
                            ) {
                                Text(
                                    text = "Wrong",
                                    fontSize = 20.sp
                                )
                            }
                            Button(
                                onClick = {
                                    onLearnEvent(LearnEvent.FlipFlashcard)
                                    onLearnEvent(LearnEvent.CorrectCard)
                                    onLearnEvent(LearnEvent.GetRandomFlashcard)
                                },
                                modifier = Modifier
                                    .size(125.dp,50.dp),
                            ) {
                                Text(
                                    text = "Correct",
                                    fontSize = 20.sp
                                )
                            }
                            Button(
                                onClick = {
                                    onLearnEvent(LearnEvent.FlipFlashcard)
                                    onLearnEvent(LearnEvent.EasyCard)
                                    onLearnEvent(LearnEvent.GetRandomFlashcard)
                                },
                                modifier = Modifier
                                    .size(125.dp,50.dp),
                            ) {
                                Text(
                                    text = "Easy",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    } else {
                        Button(
                            onClick = { onLearnEvent(LearnEvent.FlipFlashcard) },
                            modifier = Modifier
                                .size(225.dp,100.dp),
                        ) {
                            Text(
                                text = "Show Answer",
                                fontSize = 30.sp
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(300.dp,100.dp)
                ) {
                    Text(
                        text = "No New Kanji Available",
                        modifier = modifier.align(Alignment.Center),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LearnScreenPreview() {
    LearnScreen(modifier = Modifier, navController = rememberNavController(), learnState = LearnState(), onLearnEvent = { })
}