package com.example.kanjimemorized.ui.screens.study

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
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
import com.example.kanjimemorized.ui.theme.spacing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun StudyPlaygroundScreen(
    modifier: Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(spacing.small),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(5.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigateUp()
                    }
            ) {
                Text(
                    text = "Playground",
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
        var boxColorState: Color by remember { mutableStateOf(Color.White) }
        val boxColorAnimation: Color by animateColorAsState(
            targetValue = boxColorState,
            animationSpec = tween(durationMillis = 500),
            label = ""
        )
        var selectedChoice: String? by remember { mutableStateOf(null) }
        Box(
            modifier = Modifier
                .size(
                    width = 150.dp,
                    height = 5.dp
                )
                .background(boxColorAnimation)
                .align(alignment = CenterHorizontally)
        )
        choiceList.forEach { choice ->
            AnswerChoice(
                choice = choice,
                selected = mutableStateOf(choice == selectedChoice),
                updateBoxColor = { boxColorState = it },
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
    val infiniteTransition: InfiniteTransition = rememberInfiniteTransition(label = "")
    val containerColor: Color = if (selected.value) {
        infiniteTransition.animateColor(
            initialValue = Color.DarkGray,
            targetValue = Color.Gray,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        ).value
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
fun StudyPlaygroundScreenPreview() {
    StudyPlaygroundScreen(Modifier, rememberNavController(), SnackbarHostState(), rememberCoroutineScope())
}