package com.example.kanjimemorized.ui.screens.study

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardEvent
import com.example.kanjimemorized.ui.screens.study.flashcard.StudyType
import com.example.kanjimemorized.ui.screens.study.learn.LearnEvent
import com.example.kanjimemorized.ui.screens.study.review.ReviewEvent
import com.example.kanjimemorized.ui.theme.spacing
import java.lang.Thread.sleep

@Composable
fun StudyScreen(
    modifier: Modifier,
    navController: NavHostController,
    bottomNavBar: @Composable () -> Unit,
    onLearnEvent: (LearnEvent) -> Unit,
    onReviewEvent: (ReviewEvent) -> Unit,
    onFlashcardEvent: (FlashcardEvent) -> Unit
) {
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
            modifier = modifier
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
                    text = "Study",
                    modifier = Modifier
                        .align(alignment = Alignment.Center),
                    fontSize = 50.sp
                )
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        Log.d("StudyScreen.kt", "Navigating to Playground Screen...")
                        navController.navigate(
                            route = Screen.StudyPlayground.route
                        )
                    }
                ) {
                    Text(
                        text = "Playground",
                        fontSize = 35.sp
                    )
                }
                Button(
                    onClick = {
                        onFlashcardEvent(FlashcardEvent.SetStudyType(StudyType.New))
                        onFlashcardEvent(FlashcardEvent.InitializeQueue)
                        //onLearnEvent(LearnEvent.InitializeQueue)
                        Log.d("StudyScreen.kt", "Navigating to Learn Screen...")
                        navController.navigate(
                            route = Screen.Flashcard.route
                            //route = Screen.Learn.route
                        )
                    }
                ) {
                    Text(
                        text = "Learn",
                        fontSize = 35.sp
                    )
                }
                Button(
                    onClick = {
                        onFlashcardEvent(FlashcardEvent.SetStudyType(StudyType.Review))
                        onFlashcardEvent(FlashcardEvent.InitializeQueue)
                        //onReviewEvent(ReviewEvent.InitializeQueue)
                        Log.d("StudyScreen.kt", "Navigating to Review Screen...")
                        navController.navigate(
                            route = Screen.Flashcard.route
                            //route = Screen.Review.route
                        )
                    }
                ) {
                    Text(
                        text = "Review",
                        fontSize = 35.sp
                    )
                }
                Button(
                    onClick = {
                        onFlashcardEvent(FlashcardEvent.GetRandomFlashcard)
                        Log.d("StudyScreen.kt", "Navigating to Flashcard Screen...")
                        navController.navigate(
                            route = Screen.Flashcard.route
                        )
                    }
                ) {
                    Text(
                        text = "Flashcard",
                        fontSize = 35.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudyScreenPreview() {
    StudyScreen(Modifier, rememberNavController(), { }, onLearnEvent = { }, onReviewEvent = { }, onFlashcardEvent = { })
}