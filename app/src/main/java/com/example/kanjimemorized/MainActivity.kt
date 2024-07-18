package com.example.kanjimemorized

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.DatabaseModule.provideDao
import com.example.kanjimemorized.DatabaseModule.provideDatabase
import com.example.kanjimemorized.DatabaseModule.provideRepository
import com.example.kanjimemorized.ui.BottomNavBar
import com.example.kanjimemorized.ui.screens.library.LibraryEvent
import com.example.kanjimemorized.ui.screens.library.LibraryViewModel
import com.example.kanjimemorized.ui.SetupNavGraph
import com.example.kanjimemorized.ui.screens.library.LibraryViewModelFactory
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiEvent
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiViewModel
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiViewModelFactory
import com.example.kanjimemorized.ui.screens.statistics.StatisticsEvent
import com.example.kanjimemorized.ui.screens.statistics.StatisticsViewModel
import com.example.kanjimemorized.ui.screens.statistics.StatisticsViewModelFactory
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardEvent
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardViewModel
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardViewModelFactory
import com.example.kanjimemorized.ui.screens.study.learn.LearnEvent
import com.example.kanjimemorized.ui.screens.study.learn.LearnViewModel
import com.example.kanjimemorized.ui.screens.study.learn.LearnViewModelFactory
import com.example.kanjimemorized.ui.screens.study.review.ReviewEvent
import com.example.kanjimemorized.ui.screens.study.review.ReviewViewModel
import com.example.kanjimemorized.ui.screens.study.review.ReviewViewModelFactory
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val kanjiRepository = provideRepository(provideDao(provideDatabase(applicationContext)))



        val libraryViewModelFactory = LibraryViewModelFactory(kanjiRepository)
        val libraryViewModel: LibraryViewModel = ViewModelProvider(
            owner = this,
            factory = libraryViewModelFactory
        )[LibraryViewModel::class.java]

        val kanjiViewModelFactory = KanjiViewModelFactory(kanjiRepository)
        val kanjiViewModel: KanjiViewModel = ViewModelProvider(
            owner = this,
            factory = kanjiViewModelFactory
        )[KanjiViewModel::class.java]

        val learnViewModelFactory = LearnViewModelFactory(kanjiRepository)
        val learnViewModel: LearnViewModel = ViewModelProvider(
            owner = this,
            factory = learnViewModelFactory
        )[LearnViewModel::class.java]

        val reviewViewModelFactory = ReviewViewModelFactory(kanjiRepository)
        val reviewViewModel: ReviewViewModel = ViewModelProvider(
            owner = this,
            factory = reviewViewModelFactory
        )[ReviewViewModel::class.java]

        val flashcardViewModelFactory = FlashcardViewModelFactory(kanjiRepository)
        val flashcardViewModel: FlashcardViewModel = ViewModelProvider(
            owner = this,
            factory = flashcardViewModelFactory
        )[FlashcardViewModel::class.java]

        val statisticsViewModelFactory = StatisticsViewModelFactory(kanjiRepository)
        val statisticsViewModel: StatisticsViewModel = ViewModelProvider(
            owner = this,
            factory = statisticsViewModelFactory
        )[StatisticsViewModel::class.java]

        setContent {
            KanjiMemorizedTheme {
                val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
                val coroutineScope: CoroutineScope = rememberCoroutineScope()

                val libraryState by libraryViewModel.state.collectAsState()
                val kanjiState by kanjiViewModel.state.collectAsState()
                val learnState by learnViewModel.state.collectAsState()
                val reviewState by reviewViewModel.state.collectAsState()
                val flashcardState by flashcardViewModel.state.collectAsState()
                val statisticsState by statisticsViewModel.state.collectAsState()

                val onLibraryEvent: (LibraryEvent) -> Unit = libraryViewModel::onEvent
                val onKanjiEvent:(KanjiEvent) -> Unit = kanjiViewModel::onEvent
                val onLearnEvent: (LearnEvent) -> Unit = learnViewModel::onEvent
                val onReviewEvent: (ReviewEvent) -> Unit = reviewViewModel::onEvent
                val onFlashcardEvent: (FlashcardEvent) -> Unit = flashcardViewModel::onEvent
                val onStatisticsEvent: (StatisticsEvent) -> Unit = statisticsViewModel::onEvent
                    SetupNavGraph(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        //navController = navController,
                        //bottomNavBar = { BottomNavBar(navController) },
                        snackbarHostState =  snackbarHostState,
                        coroutineScope = coroutineScope,
                        libraryState = libraryState,
                        kanjiState = kanjiState,
                        learnState = learnState,
                        reviewState = reviewState,
                        flashcardState = flashcardState,
                        onLibraryEvent = onLibraryEvent,
                        onKanjiEvent = onKanjiEvent,
                        onLearnEvent = onLearnEvent,
                        onReviewEvent = onReviewEvent,
                        onFlashcardEvent = onFlashcardEvent,
                        onStatisticsEvent = onStatisticsEvent
                    )

                }
            }
        }
    }
