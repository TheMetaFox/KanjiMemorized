package com.example.kanjimemorized.ui.screens.study

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.theme.spacing

@Composable
fun StudyScreen(
    modifier: Modifier,
    navController: NavHostController,
    ) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(MaterialTheme.spacing.small),

        ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    navController.navigate(
                        route = Screen.StudyPlayground.route
                    )
                }
            ) {
                Text(
                    text = "Playground",
                    fontSize = 35.sp
                )
            }
            Button(
                onClick = {
                    navController.navigate(
                        route = Screen.Flashcard.route
                    )
                }
            ) {
                Text(
                    text = "Flashcard",
                    fontSize = 35.sp
                )
                
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun StudyScreenPreview() {
    StudyScreen(Modifier, rememberNavController())
}