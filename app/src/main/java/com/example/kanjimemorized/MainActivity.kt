package com.example.kanjimemorized

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.kanjimemorized.DatabaseModule.provideDao
import com.example.kanjimemorized.DatabaseModule.provideDatabase
import com.example.kanjimemorized.DatabaseModule.provideRepository
import com.example.kanjimemorized.ui.SetupNavGraph
import com.example.kanjimemorized.ui.screens.home.HomeEvent
import com.example.kanjimemorized.ui.screens.home.HomeViewModel
import com.example.kanjimemorized.ui.screens.home.HomeViewModelFactory
import com.example.kanjimemorized.ui.screens.home.flashcard.FlashcardEvent
import com.example.kanjimemorized.ui.screens.home.flashcard.FlashcardViewModel
import com.example.kanjimemorized.ui.screens.home.flashcard.FlashcardViewModelFactory
import com.example.kanjimemorized.ui.screens.library.LibraryEvent
import com.example.kanjimemorized.ui.screens.library.LibraryViewModel
import com.example.kanjimemorized.ui.screens.library.LibraryViewModelFactory
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiEvent
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiViewModel
import com.example.kanjimemorized.ui.screens.library.kanji.KanjiViewModelFactory
import com.example.kanjimemorized.ui.screens.settings.SettingsEvent
import com.example.kanjimemorized.ui.screens.settings.SettingsViewModel
import com.example.kanjimemorized.ui.screens.settings.SettingsViewModelFactory
import com.example.kanjimemorized.ui.screens.statistics.StatisticsEvent
import com.example.kanjimemorized.ui.screens.statistics.StatisticsViewModel
import com.example.kanjimemorized.ui.screens.statistics.StatisticsViewModelFactory
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("MainActivity.kt", "Started onCreate()...")
        super.onCreate(savedInstanceState)

        val kanjiRepository = provideRepository(provideDao(provideDatabase(applicationContext)))

        val homeViewModelFactory = HomeViewModelFactory(kanjiRepository)
        val homeViewModel: HomeViewModel = ViewModelProvider(
            owner = this,
            factory = homeViewModelFactory
        )[HomeViewModel::class.java]

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

        val settingsViewModelFactory = SettingsViewModelFactory(kanjiRepository)
        val settingsViewModel: SettingsViewModel = ViewModelProvider(
            owner = this,
            factory = settingsViewModelFactory
        )[SettingsViewModel::class.java]


        val onHomeEvent: (HomeEvent) -> Unit = homeViewModel::onEvent
        val onLibraryEvent: (LibraryEvent) -> Unit = libraryViewModel::onEvent
        val onKanjiEvent: (KanjiEvent) -> Unit = kanjiViewModel::onEvent
        val onFlashcardEvent: (FlashcardEvent) -> Unit = flashcardViewModel::onEvent
        val onStatisticsEvent: (StatisticsEvent) -> Unit = statisticsViewModel::onEvent
        val onSettingsEvent: (SettingsEvent) -> Unit = settingsViewModel::onEvent

        onHomeEvent(HomeEvent.LoadHomeData)
        onStatisticsEvent(StatisticsEvent.LoadStatisticsData)
        onSettingsEvent(SettingsEvent.LoadSettingsData)

        installSplashScreen().apply {
            setKeepOnScreenCondition(
                condition = {
                    homeViewModel.isLoading.value || statisticsViewModel.isLoading.value || settingsViewModel.isLoading.value
                }
            )
        }

        setContent {
            KanjiMemorizedTheme {
                val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
                val coroutineScope: CoroutineScope = rememberCoroutineScope()

                val homeState by homeViewModel.state.collectAsState()
                val libraryState by libraryViewModel.state.collectAsState()
                val kanjiState by kanjiViewModel.state.collectAsState()
                val flashcardState by flashcardViewModel.state.collectAsState()
                val statisticsState by statisticsViewModel.state.collectAsState()
                val settingsState by settingsViewModel.state.collectAsState()

                LaunchedEffect(null) {
                    Firebase.crashlytics.isCrashlyticsCollectionEnabled = kanjiRepository.getSettingsFromCode(code = "crashlytics_enabled").setValue.toBooleanStrict()
                    FirebaseModule.firebaseAnalytics.setAnalyticsCollectionEnabled(kanjiRepository.getSettingsFromCode(code = "analytics_enabled").setValue.toBooleanStrict())
                    Log.i("MainActivity.kt", "FB Crashlytics: ${Firebase.crashlytics.isCrashlyticsCollectionEnabled}")
                }
                FirebaseModule.firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)

                SetupNavGraph(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope,
                    homeState = homeState,
                    libraryState = libraryState,
                    kanjiState = kanjiState,
                    flashcardState = flashcardState,
                    statisticsState = statisticsState,
                    settingsState = settingsState,
                    onHomeEvent = onHomeEvent,
                    onLibraryEvent = onLibraryEvent,
                    onKanjiEvent = onKanjiEvent,
                    onFlashcardEvent = onFlashcardEvent,
                    onStatisticsEvent = onStatisticsEvent,
                    onSettingsEvent = onSettingsEvent
                )
            }
        }
    }
}