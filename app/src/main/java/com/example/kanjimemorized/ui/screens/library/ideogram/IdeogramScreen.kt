package com.example.kanjimemorized.ui.screens.library.ideogram

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.screens.library.CircularProgressBar
import com.example.kanjimemorized.ui.theme.spacing

@Composable
fun IdeogramScreen(
    modifier: Modifier,
    navController: NavController,
    ideogramState: IdeogramState,
    onIdeogramEvent: (IdeogramEvent) -> Unit
) {
    Scaffold (
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                start = MaterialTheme.spacing.medium,
                top = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium
            )
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.Library.route)
                    }
                    .padding(bottom = 5.dp),
            ) {
                Text(
                    text = "Ideogram",
                    modifier = Modifier
                        .align(alignment = Alignment.Center),
                    fontSize = 50.sp
                )
            }

            Row(
                modifier = Modifier
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .weight(1f),
                ) {
                    Text(
                        text = ideogramState.ideogram?.unicode.toString(),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 100.sp,
                        lineHeight = 50.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Column() {
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Retention:",
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = ideogramState.ideogram?.retention.toString(),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 34.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                    }
                    Column() {
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = "Coercivity:",
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 24.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Box(
                            modifier = Modifier
                        ) {
                            Text(
                                text = ideogramState.ideogram?.coercivity.toString(),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 34.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .width(600.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Meanings: ",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = ideogramState.ideogram?.meanings.toString().replace("[", "").replace("]",""),
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                }
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Strokes: ",
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Box(
                        modifier = Modifier
                    ) {
                        Text(
                            text = ideogramState.ideogram?.strokes.toString(),
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
            Column {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IdeogramScreenPreview() {
    IdeogramScreen(modifier = Modifier, navController = rememberNavController(), ideogramState = IdeogramState(), onIdeogramEvent = {})
}