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
import androidx.room.Room
import com.example.kanjimemorized.data.IdeogramDatabase
import com.example.kanjimemorized.data.IdeogramEvent
import com.example.kanjimemorized.data.IdeogramRepository
import com.example.kanjimemorized.ui.screens.ideogram.IdeogramViewModel
import com.example.kanjimemorized.ui.SetupNavGraph
import com.example.kanjimemorized.ui.screens.ideogram.IdeogramViewModelFactory
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database by lazy(
            initializer = {
                Room.databaseBuilder(
                    context = applicationContext,
                    klass = IdeogramDatabase::class.java,
                    name = "Ideogram.db"
                ).fallbackToDestructiveMigration().build()
            }
        )
        val ideogramRepository = IdeogramRepository(database.ideogramDao)
        val ideogramViewModelFactory = IdeogramViewModelFactory(ideogramRepository)
        val ideogramViewModel: IdeogramViewModel = ViewModelProvider(
            owner = this,
            factory = ideogramViewModelFactory
        )[IdeogramViewModel::class.java]

        setContent {
            KanjiMemorizedTheme {
                val navController: NavHostController = rememberNavController()
                val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
                val coroutineScope: CoroutineScope = rememberCoroutineScope()
                val state by ideogramViewModel.state.collectAsState()
                val onEvent: (IdeogramEvent) -> Unit = ideogramViewModel::onEvent
                SetupNavGraph(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    navController = navController,
                    snackbarHostState =  snackbarHostState,
                    coroutineScope = coroutineScope,
                    state = state,
                    onEvent = onEvent
                )
            }
        }
    }
}
