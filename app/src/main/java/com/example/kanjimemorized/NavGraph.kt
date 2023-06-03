package com.example.kanjimemorized

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.CoroutineScope

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    contentPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    state: IdeogramState,
    onEvent: (IdeogramEvent) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.Study.route
        ) {
            StudyScreen(
                navController = navController,
                contentPadding = contentPadding,
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )
        }
        composable(
            route = Screen.Ideogram.route
        ) {
            IdeogramScreen(
                navController = navController,
                state = state,
                onEvent = onEvent)
        }
    }
}