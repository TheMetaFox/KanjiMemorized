package com.example.kanjimemorized.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.screens.home.HomeEvent
import com.example.kanjimemorized.ui.screens.home.HomeScreen
import com.example.kanjimemorized.ui.screens.home.HomeState
import com.example.kanjimemorized.ui.screens.library.LibraryEvent
import com.example.kanjimemorized.ui.screens.library.LibraryScreen
import com.example.kanjimemorized.ui.screens.library.LibraryState
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiEvent
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiScreen
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiState
import com.example.kanjimemorized.ui.screens.settings.SettingsEvent
import com.example.kanjimemorized.ui.screens.settings.SettingsScreen
import com.example.kanjimemorized.ui.screens.settings.SettingsState
import com.example.kanjimemorized.ui.screens.statistics.StatisticsEvent
import com.example.kanjimemorized.ui.screens.statistics.StatisticsScreen
import com.example.kanjimemorized.ui.screens.statistics.StatisticsState
import com.example.kanjimemorized.ui.screens.study.StudyPlaygroundScreen
import com.example.kanjimemorized.ui.screens.home.flashcard.FlashcardEvent
import com.example.kanjimemorized.ui.screens.home.flashcard.FlashcardScreen
import com.example.kanjimemorized.ui.screens.home.flashcard.FlashcardState
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SetupNavGraph(
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    homeState: HomeState,
    libraryState: LibraryState,
    kanjiState: KanjiState,
    flashcardState: FlashcardState,
    statisticsState: StatisticsState,
    settingsState: SettingsState,
    onHomeEvent: (HomeEvent) -> Unit,
    onLibraryEvent: (LibraryEvent) -> Unit,
    onKanjiEvent: (KanjiEvent) -> Unit,
    onFlashcardEvent: (FlashcardEvent) -> Unit,
    onStatisticsEvent: (StatisticsEvent) -> Unit,
    onSettingsEvent: (SettingsEvent) -> Unit,
) {
    SharedTransitionLayout {
        val navController: NavHostController = rememberNavController()

        NavHost(
        navController = navController,
        startDestination = Screen.Home.route
        ) {
            composable(
                route = Screen.Home.route
            ) {
                HomeScreen(
                    modifier = modifier,
                    navController = navController,
                    homeState = homeState,
                    onHomeEvent = onHomeEvent,
                    onFlashcardEvent = onFlashcardEvent
                )
            }
            composable(
                route = Screen.StudyPlayground.route
            ) {
                StudyPlaygroundScreen(
                    modifier = modifier,
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope
                )
            }
            composable(
                route = Screen.Flashcard.route
            ) {
                FlashcardScreen(
                    modifier = modifier,
                    navController = navController,
                    flashcardState = flashcardState,
                    onFlashcardEvent = onFlashcardEvent,
                    onKanjiEvent = onKanjiEvent
                )
            }
            composable(
                route = Screen.Library.route
            ) {
                LibraryScreen(
                    modifier = modifier,
                    navController = navController,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    libraryState = libraryState,
                    onLibraryEvent = onLibraryEvent,
                    onKanjiEvent = onKanjiEvent,
                )
            }
            composable(
                route = Screen.Kanji.route
            ) {
                KanjiScreen(
                    modifier = modifier,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable,
                    navController = navController,
                    kanjiState = kanjiState,
                    onKanjiEvent = onKanjiEvent
                )
            }
            composable(
                route = Screen.Statistics.route
            ) {
                StatisticsScreen(
                    navController = navController,
                    statisticsState = statisticsState,
                    onStatisticsEvent = onStatisticsEvent
                )
            }
            composable(
                route = Screen.Settings.route
            ) {
                SettingsScreen(
                    modifier = modifier,
                    navController = navController,
                    settingsState = settingsState,
                    onSettingsEvent = onSettingsEvent
                )
            }
        }
    }
}