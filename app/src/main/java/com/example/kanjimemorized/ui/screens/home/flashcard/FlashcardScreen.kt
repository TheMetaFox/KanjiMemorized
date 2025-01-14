package com.example.kanjimemorized.ui.screens.home.flashcard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.LoadingAnimation
import com.example.kanjimemorized.LoadingAnimation2
import com.example.kanjimemorized.data.entities.Kanji
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiEvent

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
            .background(color = MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(insets = WindowInsets.statusBars)
            .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = { Text("Flashcard") },
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
                    .fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Box(
                    modifier = Modifier
                        .clickable {
                            navController.navigateUp()
                        }
                ) {
                    Text(
                        text = "Flashcard",
                        modifier = Modifier
                            .align(alignment = Center),
                        fontSize = 50.sp
                    )
                }
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
                                        fontSize = 20.sp
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
                                            .size(125.dp, 50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.tertiary
                                        )
                                    ) {
                                        Text(
                                            text = "Wrong",
                                            fontSize = 20.sp
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            onFlashcardEvent(FlashcardEvent.PlayAnimation)
                                            onFlashcardEvent(FlashcardEvent.ProcessCard(rating = Rating.CORRECT))
                                            onFlashcardEvent(FlashcardEvent.FlipFlashcard)
                                        },
                                        modifier = Modifier
                                            .size(125.dp, 50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.secondary
                                        )
                                    ) {
                                        Text(
                                            text = "Correct",
                                            fontSize = 20.sp
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            onFlashcardEvent(FlashcardEvent.PlayAnimation)
                                            onFlashcardEvent(FlashcardEvent.ProcessCard(rating = Rating.EASY))
                                            onFlashcardEvent(FlashcardEvent.FlipFlashcard)
                                        },
                                        modifier = Modifier
                                            .size(125.dp, 50.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Text(
                                            text = "Easy",
                                            fontSize = 20.sp
                                        )
                                    }
                                }
                            }
                        } else {
                            Button(
                                onClick = { onFlashcardEvent(FlashcardEvent.FlipFlashcard) },
                                modifier = Modifier
                                    .size(250.dp, 100.dp),
                            ) {
                                Text(
                                    text = "Show Answer",
                                    fontSize = 30.sp,
                                    textAlign = TextAlign.Center
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
                            text = "No New Kanji Available",
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

@Preview
@Composable
fun ReviewUnavailablePreview() {
    FlashcardScreen(
        modifier = Modifier,
        navController = rememberNavController(),
        flashcardState = FlashcardState(isLoading = false),
        onFlashcardEvent = { },
        onKanjiEvent = { }
    )
}

@Preview
@Composable
fun FlashcardKanjiPreview() {
    FlashcardScreen(
        modifier = Modifier,
        navController = rememberNavController(),
        flashcardState = FlashcardState(
            kanji = Kanji(unicode = Char(30000), strokes = 3),
            isReviewAvailable = true,
            isLoading = false
        ),
        onFlashcardEvent = { },
        onKanjiEvent = { }
    )
}

@Preview
@Composable
fun FlashcardAnswerPreview() {
    FlashcardScreen(
        modifier = Modifier,
        navController = rememberNavController(),
        flashcardState = FlashcardState(
            kanji = Kanji(unicode = Char(30000), strokes = 3),
            meanings = listOf("rice field"),
            isAnswerShowing = true,
            isReviewAvailable = true,
            isLoading = false
        ),
        onFlashcardEvent = { },
        onKanjiEvent = { }
    )
}