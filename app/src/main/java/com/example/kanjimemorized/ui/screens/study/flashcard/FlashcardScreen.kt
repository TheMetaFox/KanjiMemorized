package com.example.kanjimemorized.ui.screens.study.flashcard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kanjimemorized.ui.screens.ideogram.IdeogramEvent
import com.example.kanjimemorized.ui.theme.spacing
@Composable
fun FlashcardScreen(
    modifier: Modifier,
    flashcardState: FlashcardState,
    onFlashcardEvent: (FlashcardEvent) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(MaterialTheme.spacing.small),
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                modifier = Modifier
                    .size(200.dp, 175.dp)
                    .clickable { onFlashcardEvent(FlashcardEvent.FlipFlashcard) }
            ) {
                if (!flashcardState.isAnswerShowing) {
                    Ideogram(
                        modifier = modifier
                    )
                }
                else {
                    Meaning(
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
fun Ideogram(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "一",
            modifier = modifier.align(Center),
            fontSize = 100.sp,
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun Meaning(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "one",
            modifier = modifier.align(Center),
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun FlashcardScreenPreview() {
    FlashcardScreen(modifier = Modifier, flashcardState = FlashcardState(), onFlashcardEvent = { })
}