package com.example.kanjimemorized.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
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
import com.example.kanjimemorized.ui.BottomNavBar
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.screens.home.flashcard.FlashcardEvent
import com.example.kanjimemorized.ui.screens.home.flashcard.StudyType
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    homeState: HomeState,
    onHomeEvent: (HomeEvent) -> Unit,
    onFlashcardEvent: (FlashcardEvent) -> Unit
) {
    onHomeEvent(HomeEvent.LoadHomeData)
    Scaffold(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(insets = WindowInsets.statusBars)
            .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = { Text(text = "Home") },
                actions = {
                    var showDropdownMenu by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = { showDropdownMenu = true }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "more vertical"
                        )
                    }
                    val localUriHandler: UriHandler = LocalUriHandler.current
                    DropdownMenu(
                        expanded = showDropdownMenu,
                        onDismissRequest = { showDropdownMenu = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text("App Guide") },
                            onClick = {
                                showDropdownMenu = false
                                localUriHandler.openUri("https://github.com/TheMetaFox/KanjiMemorized?tab=readme-ov-file#app-guide")
                            },
                            leadingIcon = { Icon(Icons.Outlined.Info, "info") }
                        )
                        DropdownMenuItem(
                            text = { Text("Feedback") },
                            onClick = {
                                showDropdownMenu = false
                                localUriHandler.openUri("https://docs.google.com/forms/d/e/1FAIpQLScQzby5vRCzXCFfAlnzWv6iUmuMwS1J6PlYcG7HzOxW8hTwnw/viewform?usp=sf_link")
                            },
                            leadingIcon = { Icon(Icons.Outlined.Feedback, "feedback") }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                )
            )
        },
        bottomBar = {
            BottomNavBar(selected = "Home", navController = navController)
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .padding(paddingValues = contentPadding)
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
            Column(
                modifier = modifier
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
                        fontSize = 32.sp,
                    )
                }
                Box(
                    modifier = Modifier,
                ) {
                    Text(
                        text = "Reviews: ${homeState.currentReviewCount}",
                        modifier = Modifier
                            .align(alignment = Center),
                        fontSize = 32.sp,
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(space = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            onFlashcardEvent(FlashcardEvent.SetStudyType(StudyType.MIXED))
                            onFlashcardEvent(FlashcardEvent.InitializeQueue)
                            navController.navigate(route = Screen.Flashcard.route)
                        },
                    ) {
                        Text(
                            text = "Guided Study",
                            fontSize = 32.sp,
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                onFlashcardEvent(FlashcardEvent.SetStudyType(StudyType.NEW))
                                onFlashcardEvent(FlashcardEvent.InitializeQueue)
                                navController.navigate(route = Screen.Flashcard.route)
                            },
                        ) {
                            Text(
                                text = "Learn",
                                fontSize = 32.sp
                            )
                        }
                        Button(
                            onClick = {
                                onFlashcardEvent(FlashcardEvent.SetStudyType(StudyType.REVIEW))
                                onFlashcardEvent(FlashcardEvent.InitializeQueue)
                                navController.navigate(route = Screen.Flashcard.route)
                            },
                        ) {
                            Text(
                                text = "Review",
                                fontSize = 32.sp
                            )
                        }
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
                            fontSize = 32.sp
                        )
                    ) {
                        append(text = "K")
                    }
                    append(text = "anji ")
                    withStyle(
                        style = SpanStyle(
                            fontSize = 32.sp
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
                    fontSize = 26.sp
                )
            )
        }
    }
}

@Preview(name = "Light", group = "LightDark")
@Preview(name = "Dark", group = "LightDark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview_LightDark() {
    KanjiMemorizedTheme {
        HomeScreen(navController = rememberNavController(), homeState = HomeState(), onHomeEvent = { }, onFlashcardEvent = { })
    }
}
@Preview(name = "85%", group = "FontScale", fontScale = 0.85f)
@Preview(name = "150%", group = "FontScale", fontScale = 1.5f)
@Preview(name = "200%", group = "FontScale", fontScale = 2f)
@Composable
fun HomeScreenPreview_FontScale() {
    KanjiMemorizedTheme {
        HomeScreen(navController = rememberNavController(), homeState = HomeState(), onHomeEvent = { }, onFlashcardEvent = { })
    }
}
