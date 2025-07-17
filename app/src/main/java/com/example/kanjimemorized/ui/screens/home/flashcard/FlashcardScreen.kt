package com.example.kanjimemorized.ui.screens.home.flashcard

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.LoadingAnimation
import com.example.kanjimemorized.LoadingAnimation2
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.TopBar
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiEvent
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(
    modifier: Modifier,
    navController: NavHostController,
    flashcardState: FlashcardState,
    onFlashcardEvent: (FlashcardEvent) -> Unit,
    onKanjiEvent: (KanjiEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .windowInsetsPadding(insets = WindowInsets.statusBars),
        topBar = {
            TopBar(title = "Flashcard")
        }
    ) { contentPadding ->
        if (flashcardState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Center
            ) {
                LoadingAnimation()
            }
        } else {
            Column(
                modifier = modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(MaterialTheme.colorScheme.outline, MaterialTheme.colorScheme.background),
                            endY = 50f
                        )
                    )
                    .padding(8.dp),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                if (flashcardState.isReviewAvailable) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(350.dp),
                            contentAlignment = Center
                        ) {
                             Column(
                                horizontalAlignment = CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(150.dp, 150.dp)
                                ) {
                                    Text(
                                        text = flashcardState.kanji?.unicode.toString(),
                                        modifier = modifier.align(Center),
                                        fontSize = 100.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                if (flashcardState.isAnswerShowing) {
                                    Box(
                                        modifier = Modifier
                                            .size(400.dp, 100.dp)
                                    ) {
                                        Text(
                                            text = flashcardState.meanings.toString()
                                                .replace("[", "")
                                                .replace("]", ""),
                                            modifier = modifier.align(Center),
                                            fontSize = 40.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                            LoadingAnimation2(
                                color = when (flashcardState.lastRating) {
                                    1 -> MaterialTheme.colorScheme.tertiary
                                    2 -> MaterialTheme.colorScheme.secondary
                                    3 -> MaterialTheme.colorScheme.primary
                                    else -> Color.DarkGray
                                },
                                isAnimationPlaying = flashcardState.isAnimationPlaying
                            )
                        }
                        if (flashcardState.isAnswerShowing) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                horizontalAlignment = CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        onKanjiEvent(KanjiEvent.DisplayKanjiInfo(flashcardState.kanji!!))
                                        navController.navigate(Screen.Kanji.route)
                                    },
                                    modifier = Modifier
                                        .size(375.dp, 50.dp),
                                ) {
                                    Text(
                                        text = "View Kanji",
                                        fontSize = 16.sp
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        onClick = {
                                            onFlashcardEvent(FlashcardEvent.PlayAnimation)
                                            onFlashcardEvent(FlashcardEvent.ProcessCard(rating = Rating.WRONG))
                                            onFlashcardEvent(FlashcardEvent.FlipFlashcard)
                                        },
                                        modifier = Modifier
                                            .size(120.dp, 50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.tertiary
                                        ),
                                        contentPadding = PaddingValues(5.dp)
                                    ) {
                                        Text(
                                            text = "Wrong",
                                            fontSize = 16.sp
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            onFlashcardEvent(FlashcardEvent.PlayAnimation)
                                            onFlashcardEvent(FlashcardEvent.ProcessCard(rating = Rating.CORRECT))
                                            onFlashcardEvent(FlashcardEvent.FlipFlashcard)
                                        },
                                        modifier = Modifier
                                            .size(120.dp, 50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.secondary
                                        ),
                                        contentPadding = PaddingValues(5.dp)
                                    ) {
                                        Text(
                                            text = "Correct",
                                            fontSize = 16.sp
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            onFlashcardEvent(FlashcardEvent.PlayAnimation)
                                            onFlashcardEvent(FlashcardEvent.ProcessCard(rating = Rating.EASY))
                                            onFlashcardEvent(FlashcardEvent.FlipFlashcard)
                                        },
                                        modifier = Modifier
                                            .size(120.dp, 50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                        ),
                                        contentPadding = PaddingValues(5.dp)
                                    ) {
                                        Text(
                                            text = "Easy",
                                            fontSize = 16.sp
                                        )
                                    }
                                }
                            }
                        } else {
                            Button(
                                onClick = { onFlashcardEvent(FlashcardEvent.FlipFlashcard) },
                                modifier = Modifier
                                    .sizeIn(200.dp, 75.dp, 250.dp, 200.dp)
                                ,
                            ) {
                                Text(
                                    text = "Show Answer",
                                    fontSize = 30.sp,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 30.sp
                                )
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(width = 300.dp, height = 100.dp)
                    ) {
                        Text(
                            text = when(flashcardState.studyType) {
                                StudyType.NEW -> {"No New Kanji Available"}
                                StudyType.REVIEW -> {"No Kanji Reviews Available"}
                                StudyType.MIXED -> {"Finished Guided Study"}
                            },
                            modifier = modifier.align(
                                alignment = Center
                            ),
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

class FlashcardStateParameterProvider: PreviewParameterProvider<FlashcardState> {
    override val values: Sequence<FlashcardState> get() = sequenceOf(
        FlashcardState(
            isLoading = false
        ),
        FlashcardState(
            kanji = Kanji(unicode = Char(30000), strokes = 3),
            isReviewAvailable = true,
            isLoading = false
        ),
        FlashcardState(
            kanji = Kanji(unicode = Char(30000), strokes = 3),
            meanings = listOf("rice field"),
            isAnswerShowing = true,
            isReviewAvailable = true,
            isLoading = false
        )
    )
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FlashcardScreenPreview_LightDark(
    @PreviewParameter(provider = FlashcardStateParameterProvider::class)
    flashcardState: FlashcardState
) {
    KanjiMemorizedTheme {
        FlashcardScreen(
            modifier = Modifier,
            navController = rememberNavController(),
            flashcardState = flashcardState,
            onFlashcardEvent = { },
            onKanjiEvent = { }
        )
    }
}

@Preview(name = "85%", fontScale = 0.85f)
@Preview(name = "150%", fontScale = 1.5f)
@Preview(name = "200%", fontScale = 2f)
@Composable
fun FlashcardScreenPreview_FontScale(
    @PreviewParameter(provider = FlashcardStateParameterProvider::class)
    flashcardState: FlashcardState
) {
    KanjiMemorizedTheme {
        FlashcardScreen(
            modifier = Modifier,
            navController = rememberNavController(),
            flashcardState = flashcardState,
            onFlashcardEvent = { },
            onKanjiEvent = { }
        )
    }
}