package com.example.kanjimemorized.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kanjimemorized.ui.screens.library.LibraryEvent
import com.example.kanjimemorized.ui.screens.library.LibraryState
import com.example.kanjimemorized.ui.screens.*
import com.example.kanjimemorized.ui.screens.library.LibraryScreen
import com.example.kanjimemorized.ui.screens.library.ideogram.IdeogramEvent
import com.example.kanjimemorized.ui.screens.library.ideogram.IdeogramScreen
import com.example.kanjimemorized.ui.screens.library.ideogram.IdeogramState
import com.example.kanjimemorized.ui.screens.study.StudyPlaygroundScreen
import com.example.kanjimemorized.ui.screens.study.StudyScreen
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardEvent
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardScreen
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardState
import kotlinx.coroutines.CoroutineScope

@Composable
fun SetupNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    libraryState: LibraryState,
    ideogramState: IdeogramState,
    flashcardState: FlashcardState,
    onLibraryEvent: (LibraryEvent) -> Unit,
    onIdeogramEvent: (IdeogramEvent) -> Unit,
    onFlashcardEvent: (FlashcardEvent) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable(
            route = Screen.Study.route
        ) {
            StudyScreen(
                modifier = modifier
                    .padding(5.dp),
                navController = navController)
        }
        composable(
            route = Screen.StudyPlayground.route
        ) {
            StudyPlaygroundScreen(
                modifier = modifier
                    .padding(5.dp),
                navController = navController,
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )
        }
        composable(
            route = Screen.Flashcard.route
        ) {
            FlashcardScreen(
                modifier = modifier
                    .padding(5.dp),
                flashcardState = flashcardState,
                onFlashcardEvent = onFlashcardEvent
            )
        }
        composable(
            route = Screen.Library.route
        ) {
            LibraryScreen(
                modifier = modifier
                    .padding(5.dp),
                navController = navController,
                libraryState = libraryState,
                onLibraryEvent = onLibraryEvent,
                onIdeogramEvent = onIdeogramEvent
            )
        }
        composable(
            route = Screen.Ideogram.route
        ) {
            IdeogramScreen(
                modifier = modifier,
                navController = navController,
                ideogramState = ideogramState,
                onIdeogramEvent = onIdeogramEvent
            )
        }
    }
}