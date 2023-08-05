package com.example.kanjimemorized

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.kanjimemorized.DatabaseModule.provideIdeogramData
import com.example.kanjimemorized.DatabaseModule.provideRepository
import com.example.kanjimemorized.ui.screens.library.LibraryEvent
import com.example.kanjimemorized.ui.screens.library.LibraryViewModel
import com.example.kanjimemorized.ui.SetupNavGraph
import com.example.kanjimemorized.ui.screens.library.LibraryViewModelFactory
import com.example.kanjimemorized.ui.screens.library.ideogram.IdeogramEvent
import com.example.kanjimemorized.ui.screens.library.ideogram.IdeogramViewModel
import com.example.kanjimemorized.ui.screens.library.ideogram.IdeogramViewModelFactory
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardEvent
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardViewModel
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardViewModelFactory
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val ideogramRepository = provideRepository(provideDao(provideDatabase(applicationContext)))

        provideIdeogramData(ideogramRepository)

        val libraryViewModelFactory = LibraryViewModelFactory(ideogramRepository)
        val libraryViewModel: LibraryViewModel = ViewModelProvider(
            owner = this,
            factory = libraryViewModelFactory
        )[LibraryViewModel::class.java]

        val ideogramViewModelFactory = IdeogramViewModelFactory(ideogramRepository)
        val ideogramViewModel: IdeogramViewModel = ViewModelProvider(
            owner = this,
            factory = ideogramViewModelFactory
        )[IdeogramViewModel::class.java]

        val flashcardViewModelFactory = FlashcardViewModelFactory(ideogramRepository)
        val flashcardViewModel: FlashcardViewModel = ViewModelProvider(
            owner = this,
            factory = flashcardViewModelFactory
        )[FlashcardViewModel::class.java]

        setContent {
            KanjiMemorizedTheme {
                val navController: NavHostController = rememberNavController()
                val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
                val coroutineScope: CoroutineScope = rememberCoroutineScope()

                val libraryState by libraryViewModel.state.collectAsState()
                val ideogramState by ideogramViewModel.state.collectAsState()
                val flashcardState by flashcardViewModel.state.collectAsState()

                val onLibraryEvent: (LibraryEvent) -> Unit = libraryViewModel::onEvent
                val onIdeogramEvent:(IdeogramEvent) -> Unit = ideogramViewModel::onEvent
                val onFlashcardEvent: (FlashcardEvent) -> Unit = flashcardViewModel::onEvent
                SetupNavGraph(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    navController = navController,
                    snackbarHostState =  snackbarHostState,
                    coroutineScope = coroutineScope,
                    libraryState = libraryState,
                    ideogramState = ideogramState,
                    flashcardState = flashcardState,
                    onLibraryEvent = onLibraryEvent,
                    onIdeogramEvent = onIdeogramEvent,
                    onFlashcardEvent = onFlashcardEvent,
                )
            }
        }
    }
}