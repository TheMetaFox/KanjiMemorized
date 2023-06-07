package com.example.kanjimemorized.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kanjimemorized.data.IdeogramEvent
import com.example.kanjimemorized.data.IdeogramState
import com.example.kanjimemorized.ui.screens.*
import kotlinx.coroutines.CoroutineScope

@Composable
fun SetupNavGraph(
    modifier: Modifier,
    navController: NavHostController,
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
                modifier = modifier,
                navController = navController
            )
        }
        composable(
            route = Screen.Study.route
        ) {
            StudyScreen(
                modifier = modifier
                    .padding(5.dp)
                ,
                navController = navController,
                snackbarHostState = snackbarHostState,
                coroutineScope = coroutineScope
            )
        }
        composable(
            route = Screen.Ideogram.route
        ) {
            IdeogramScreen(
                modifier = modifier
                    .padding(5.dp),
                navController = navController,
                state = state,
                onEvent = onEvent)
        }
    }
}