package com.example.kanjimemorized.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun StudyScreen(
    navController: NavHostController,
    contentPadding: PaddingValues,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    Column(
        modifier = Modifier
            .padding(contentPadding),
        horizontalAlignment = CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clickable {
                           navController.navigate(Screen.Home.route)
                },
        ) {
            Text(
                text = "Study",
                modifier = Modifier
                    .align(alignment = Center),
                fontSize = 50.sp
            )
        }
        StudyContent(
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope
        )
    }
}
@Composable
fun StudyContent(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GreetingAndAnswerChoice(
            choices = listOf("day, sun", "one", "big, large"),
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope
        )
    }
}
@Composable
fun GreetingAndAnswerChoice(
    choices: List<String>,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    var textFieldState by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Greeting()
        TextField(
            value = textFieldState,
            label = {
                Text("Translate the above sentence")
            },
            onValueChange = {
                textFieldState = it
            },
            singleLine = true,
            modifier = Modifier,
        )
        var message: String by remember { mutableStateOf("") }
        message = if (textFieldState == "Hello world!") {
            "Correct"
        } else {
            "Incorrect"
        }
        Button(
            onClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        ) {
            Text("Check Translation")
        }
        SingleChoiceQuestion(choices)
    }
}
@Composable
fun Greeting() {
    Box(modifier = Modifier
        .wrapContentSize()
    ) {
        Text(
            text = "こんにちは世界!",
            modifier = Modifier,
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SingleChoiceQuestion(choiceList: List<String>) {
    Column(
        modifier = Modifier
            .background(Color.Black),
        horizontalAlignment = Alignment.End
    )
    {
        var boxColor: Color by remember { mutableStateOf(Color.White) }
        var selectedChoice: String? by remember { mutableStateOf(null) }
        Box(
            modifier = Modifier
                .size(
                    width = 150.dp,
                    height = 5.dp
                )
                .background(boxColor)
                .align(alignment = Alignment.CenterHorizontally)
        )
        choiceList.forEach { choice ->
            AnswerChoice(
                choice = choice,
                selected = mutableStateOf(choice == selectedChoice),
                updateBoxColor = { boxColor = it },
                updateSelected = { selectedChoice = it }
            )
        }
    }
}

@Composable
fun AnswerChoice(
    choice: String,
    selected: MutableState<Boolean>,
    updateBoxColor: (Color) -> Unit,
    updateSelected: (String) -> Unit
) {
    val containerColor: Color = if (selected.value) {
        Color.Gray
    } else {
        Color.DarkGray
    }
    var borderColor: Color by remember { mutableStateOf(Color.LightGray) }
    Card(
        modifier = Modifier
            .padding(10.dp)
            .size(
                width = 150.dp,
                height = 50.dp
            )
            .clickable {
                borderColor = Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat()
                )
                updateBoxColor(
                    borderColor
                )
                updateSelected(
                    choice
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = Color.LightGray
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor)
    ) {
        Text(
            text = choice,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudyScreenPreview() {
    StudyScreen(rememberNavController(), PaddingValues(0.dp), SnackbarHostState(), rememberCoroutineScope())
}