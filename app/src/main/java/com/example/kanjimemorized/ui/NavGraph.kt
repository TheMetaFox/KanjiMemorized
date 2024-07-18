package com.example.kanjimemorized.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.screens.library.LibraryEvent
import com.example.kanjimemorized.ui.screens.library.LibraryState
import com.example.kanjimemorized.ui.screens.*
import com.example.kanjimemorized.ui.screens.library.LibraryScreen
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiEvent
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiScreen
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiState
import com.example.kanjimemorized.ui.screens.statistics.StatisticsEvent
import com.example.kanjimemorized.ui.screens.statistics.StatisticsScreen
import com.example.kanjimemorized.ui.screens.study.StudyPlaygroundScreen
import com.example.kanjimemorized.ui.screens.study.StudyScreen
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardEvent
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardScreen
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardState
import com.example.kanjimemorized.ui.screens.study.learn.LearnEvent
import com.example.kanjimemorized.ui.screens.study.learn.LearnScreen
import com.example.kanjimemorized.ui.screens.study.learn.LearnState
import com.example.kanjimemorized.ui.screens.study.review.ReviewEvent
import com.example.kanjimemorized.ui.screens.study.review.ReviewScreen
import com.example.kanjimemorized.ui.screens.study.review.ReviewState

import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SetupNavGraph(
    modifier: Modifier,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    libraryState: LibraryState,
    kanjiState: KanjiState,
    learnState: LearnState,
    reviewState: ReviewState,
    flashcardState: FlashcardState,
    onLibraryEvent: (LibraryEvent) -> Unit,
    onKanjiEvent: (KanjiEvent) -> Unit,
    onLearnEvent: (LearnEvent) -> Unit,
    onReviewEvent: (ReviewEvent) -> Unit,
    onFlashcardEvent: (FlashcardEvent) -> Unit,
    onStatisticsEvent: (StatisticsEvent) -> Unit
) {
    SharedTransitionLayout {
        val navController: NavHostController = rememberNavController()
        val bottomNavBar: @Composable () -> Unit = { BottomNavBar(navController = navController) }

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
                bottomNavBar = bottomNavBar
            )
        }
        composable(
            route = Screen.Study.route
        ) {
            StudyScreen(
                modifier = modifier,
                navController = navController,
                bottomNavBar = bottomNavBar,
                onLearnEvent = onLearnEvent,
                onReviewEvent = onReviewEvent,
                onFlashcardEvent = onFlashcardEvent)
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
            route = Screen.Learn.route
        ) {
            LearnScreen(
                modifier = modifier,
                navController = navController,
                learnState = learnState,
                onLearnEvent = onLearnEvent
            )
        }
        composable(
            route = Screen.Review.route
        ) {
            ReviewScreen(
                modifier = modifier,
                navController = navController,
                reviewState = reviewState,
                onReviewEvent = onReviewEvent
            )
        }
        composable(
            route = Screen.Flashcard.route
        ) {
            FlashcardScreen(
                modifier = modifier,
                navController = navController,
                flashcardState = flashcardState,
                onFlashcardEvent = onFlashcardEvent
            )
        }
        composable(
            route = Screen.Library.route
        ) {
            LibraryScreen(
                modifier = modifier,
                navController = navController,
                animatedVisibilityScope = this,
                bottomNavBar = bottomNavBar,
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
                animatedVisibilityScope = this,
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
                bottomNavBar = bottomNavBar,
                onStatisticsEvent = onStatisticsEvent
            )
        }
    }}
}