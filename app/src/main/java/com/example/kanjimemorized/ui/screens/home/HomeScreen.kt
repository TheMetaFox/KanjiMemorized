package com.example.kanjimemorized.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.R
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardEvent
import com.example.kanjimemorized.ui.screens.study.flashcard.StudyType
import com.example.kanjimemorized.ui.theme.spacing

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomNavBar: @Composable () -> Unit,
    homeState: HomeState,
    onHomeEvent: (HomeEvent) -> Unit,
    onFlashcardEvent: (FlashcardEvent) -> Unit
) {
    onHomeEvent(HomeEvent.LoadHomeData)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        bottomBar = {
            bottomNavBar()
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .padding(contentPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ImageCard(
                modifier = Modifier
                    .wrapContentSize(),
                painter = painterResource(R.drawable.study_anime),
                contentDescription = "An adolescent human female studying in their room.",
            )
            Box(
                modifier = Modifier,
            ) {
                Text(
                    text = "Home",
                    modifier = Modifier
                        .align(alignment = Center),
                    fontSize = 50.sp,
                )
            }
            Column(
                modifier = modifier
                    .padding(spacing.small)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier,
                ) {
                    Text(
                        text = "New Kanji: ${homeState.currentNewCount}",
                        modifier = Modifier
                            .align(alignment = Center),
                        fontSize = 36.sp,
                    )
                }
                Box(
                    modifier = Modifier,
                ) {
                    Text(
                        text = "Reviews: ${homeState.currentReviewCount}",
                        modifier = Modifier
                            .align(alignment = Center),
                        fontSize = 36.sp,
                    )
                }
                Button(
                    onClick = {
                        onFlashcardEvent(FlashcardEvent.SetStudyType(StudyType.Mixed))
                        onFlashcardEvent(FlashcardEvent.InitializeQueue)
                        navController.navigate(route = Screen.Flashcard.route)
                    }
                ) {
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Guided Review",
                            modifier = Modifier
                                .align(alignment = Center),
                        fontSize = 36.sp,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImageCard(
    modifier: Modifier,
    painter: Painter,
    contentDescription: String,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(0.dp),
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
        ){
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background
                            ),
                            startY = 0f,
                            endY = 560f
                        )
                    )
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 35.sp
                        )
                    ) {
                        append(text = "K")
                    }
                    append(text = "anji ")
                    withStyle(
                        style = SpanStyle(
                            fontSize = 35.sp
                        )
                    ) {
                        append(text = "M")
                    }
                    append(text = "emorised")
                },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 30.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController(), bottomNavBar = {}, homeState = HomeState(), onHomeEvent = { }, onFlashcardEvent = { })
}