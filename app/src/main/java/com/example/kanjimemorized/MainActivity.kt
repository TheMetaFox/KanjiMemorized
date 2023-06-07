package com.example.kanjimemorized

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.kanjimemorized.data.IdeogramDatabase
import com.example.kanjimemorized.data.IdeogramEvent
import com.example.kanjimemorized.data.IdeogramViewModel
import com.example.kanjimemorized.ui.SetupNavGraph
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController
    private val db by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = IdeogramDatabase::class.java,
            name = "Ideogram.db"
        ).fallbackToDestructiveMigration().build()
    }
    private val viewModel by viewModels<IdeogramViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return IdeogramViewModel(db.dao) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KanjiMemorizedTheme {
                navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                val state by viewModel.state.collectAsState()
                val onEvent: (IdeogramEvent) -> Unit = viewModel::onEvent
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                onEvent(IdeogramEvent.ShowDialog)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Ideogram"
                            )
                        }
                    },
                    content = { contentPadding ->
                        SetupNavGraph(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(contentPadding),
                            navController = navController,
                            snackbarHostState =  snackbarHostState,
                            coroutineScope = coroutineScope,
                            state = state,
                            onEvent = onEvent
                        )
                    }
                )
            }
        }
    }
}