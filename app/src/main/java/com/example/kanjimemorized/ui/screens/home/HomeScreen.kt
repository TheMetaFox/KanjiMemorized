package com.example.kanjimemorized.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.BottomNavBar
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.TopBar
import com.example.kanjimemorized.ui.screens.home.flashcard.FlashcardEvent
import com.example.kanjimemorized.ui.screens.home.flashcard.StudyType
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme
import java.time.LocalDate
import java.time.Period

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    homeState: HomeState,
    onHomeEvent: (HomeEvent) -> Unit,
    onFlashcardEvent: (FlashcardEvent) -> Unit
) {
    onHomeEvent(HomeEvent.LoadHomeData)
    Scaffold(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .windowInsetsPadding(insets = WindowInsets.statusBars),
        topBar = {
            TopBar(title = "Home")
        },
        bottomBar = {
            BottomNavBar(selected = "Home", navController = navController)
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .padding(paddingValues = contentPadding)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(MaterialTheme.colorScheme.outline, MaterialTheme.colorScheme.background),
                        endY = 50f
                    )
                ),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Completion Date",
                        fontSize = 14.sp
                    )
                    Text(
                        text = homeState.projectedCompletionDate.toString(),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 42.sp,
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val until: Period = Period.between(LocalDate.now(), homeState.projectedCompletionDate)
                    Text(
                        text = until.plusDays((until.years*365 + until.months*30).toLong()).days.toString(),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 42.sp
                    )
                    Text(
                        text = "Days",
                        fontSize = 14.sp
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(36.dp)
            ) {
                Box(
                    modifier = Modifier,
                ) {
                    Text(
                        text = "New Kanji: ${homeState.currentNewCount}",
                        modifier = Modifier
                            .align(alignment = Center),
                        fontSize = 32.sp,
                    )
                }
                Box(
                    modifier = Modifier,
                ) {
                    Text(
                        text = "Reviews: ${homeState.currentReviewCount}",
                        modifier = Modifier
                            .align(alignment = Center),
                        fontSize = 32.sp,
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            onFlashcardEvent(FlashcardEvent.SetStudyType(StudyType.MIXED))
                            onFlashcardEvent(FlashcardEvent.InitializeQueue)
                            navController.navigate(route = Screen.Flashcard.route)
                        },
                    ) {
                        Text(
                            text = "Guided Study",
                            fontSize = 32.sp,
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                onFlashcardEvent(FlashcardEvent.SetStudyType(StudyType.NEW))
                                onFlashcardEvent(FlashcardEvent.InitializeQueue)
                                navController.navigate(route = Screen.Flashcard.route)
                            },
                        ) {
                            Text(
                                text = "Learn",
                                fontSize = 32.sp
                            )
                        }
                        Button(
                            onClick = {
                                onFlashcardEvent(FlashcardEvent.SetStudyType(StudyType.REVIEW))
                                onFlashcardEvent(FlashcardEvent.InitializeQueue)
                                navController.navigate(route = Screen.Flashcard.route)
                            },
                        ) {
                            Text(
                                text = "Review",
                                fontSize = 32.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Light", group = "LightDark")
@Preview(name = "Dark", group = "LightDark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview_LightDark() {
    KanjiMemorizedTheme {
        HomeScreen(navController = rememberNavController(), homeState = HomeState(), onHomeEvent = { }, onFlashcardEvent = { })
    }
}
@Preview(name = "85%", group = "FontScale", fontScale = 0.85f)
@Preview(name = "150%", group = "FontScale", fontScale = 1.5f)
@Preview(name = "200%", group = "FontScale", fontScale = 2f)
@Composable
fun HomeScreenPreview_FontScale() {
    KanjiMemorizedTheme {
        HomeScreen(navController = rememberNavController(), homeState = HomeState(), onHomeEvent = { }, onFlashcardEvent = { })
    }
}
