package com.example.kanjimemorized.ui.screens

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
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import com.example.kanjimemorized.ui.theme.spacing

@Composable
fun HomeScreen(
    modifier: Modifier,
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
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
            Column(
                modifier = modifier
                    .padding(MaterialTheme.spacing.small)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
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
                Button(
                    onClick = {
                        navController.navigate(Screen.Study.route)
                    },
                    modifier = Modifier
                ) {
                    Text(
                        text = "Study",
                        modifier = Modifier.align(alignment = CenterVertically),
                        fontSize = 35.sp
                    )
                }
                Button(
                    onClick = {
                        navController.navigate(Screen.Ideogram.route)
                    },
                    modifier = Modifier
                ) {
                    Text(
                        text = "Ideogram",
                        modifier = Modifier.align(alignment = CenterVertically),
                        fontSize = 35.sp
                    )
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
                        append("K")
                    }
                    append("anji ")
                    withStyle(
                        style = SpanStyle(
                            fontSize = 35.sp
                        )
                    ) {
                        append("M")
                    }
                    append("emorised")
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
    HomeScreen(Modifier, rememberNavController())
}