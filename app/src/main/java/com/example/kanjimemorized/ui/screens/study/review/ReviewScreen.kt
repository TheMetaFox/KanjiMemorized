package com.example.kanjimemorized.ui.screens.study.review

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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.theme.spacing
@Composable
fun ReviewScreen(
    modifier: Modifier,
    navController: NavHostController,
    reviewState: ReviewState,
    onReviewEvent: (ReviewEvent) -> Unit
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
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.Study.route)
                    }
            ) {
                Text(
                    text = "Review",
                    modifier = Modifier
                        .align(alignment = Center),
                    fontSize = 50.sp
                )
            }

            if (reviewState.isReviewAvailable) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.5f),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(150.dp,100.dp)
                        ) {
                            Text(
                                text = reviewState.kanji?.unicode.toString(),
                                modifier = modifier.align(Center),
                                fontSize = 50.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                        if (reviewState.isAnswerShowing) {
                            Box(
                                modifier = Modifier
                                    .size(400.dp,100.dp)
                            ) {
                                Text(
                                    text = reviewState.meanings.toString().replace("[", "").replace("]", ""),
                                    modifier = modifier.align(Center),
                                    fontSize = 40.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    if (reviewState.isAnswerShowing) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    onReviewEvent(ReviewEvent.FlipFlashcard)
                                    onReviewEvent(ReviewEvent.WrongCard)
                                    onReviewEvent(ReviewEvent.GetRandomFlashcard)
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
                                    onReviewEvent(ReviewEvent.FlipFlashcard)
                                    onReviewEvent(ReviewEvent.CorrectCard)
                                    onReviewEvent(ReviewEvent.GetRandomFlashcard)
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
                                    onReviewEvent(ReviewEvent.FlipFlashcard)
                                    onReviewEvent(ReviewEvent.EasyCard)
                                    onReviewEvent(ReviewEvent.GetRandomFlashcard)
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
                            onClick = { onReviewEvent(ReviewEvent.FlipFlashcard) },
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
                        text = "No Reviews Available",
                        modifier = modifier.align(Center),
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
fun ReviewScreenPreview() {
    ReviewScreen(modifier = Modifier, navController = rememberNavController(), reviewState = ReviewState(), onReviewEvent = { })
}