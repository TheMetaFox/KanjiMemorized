package com.example.kanjimemorized.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.Screen
import com.example.kanjimemorized.ui.theme.spacing

@Composable
fun SettingsScreen(
    modifier: Modifier,
    navController: NavHostController,
    bottomNavBar: @Composable () -> Unit,
    settingsState: SettingsState,
    onSettingsEvent: (SettingsEvent) -> Unit,
) {
    onSettingsEvent(SettingsEvent.LoadSettingsData)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = spacing.small),
        bottomBar = {
            bottomNavBar()
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.Home.route)
                    }
            ) {
                Text(
                    text = "Settings",
                    modifier = Modifier
                        .align(alignment = Alignment.Center),
                    fontSize = 50.sp
                )
            }
            Column(
                modifier = Modifier
                    .width(350.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Box {
                            Text(
                                text = "Daily New Kanji",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 24.sp,
                            )
                        }
                        Box {
                            Text(
                                text = "Limit of new kanji introduced each day",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                    TextField(
                        value = settingsState.dailyNewKanjiField,
                        onValueChange = {
                            onSettingsEvent(SettingsEvent.UpdateTextField(field = "dailyNewKanji", text = it))
                        },
                        modifier = Modifier
                            .width(80.dp),
                        //label = { Text(text = "Current: ${settingsState.dailyNewKanji}")},
                        placeholder = { Text(text = settingsState.dailyNewKanji) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Box {
                            Text(
                                text = "Initial Ease",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 24.sp,
                            )
                        }
                        Box {
                            Text(
                                text = "Factor new kanji's durability increases by",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                    TextField(
                        value = settingsState.initialEaseField,
                        onValueChange = {
                            onSettingsEvent(SettingsEvent.UpdateTextField(field = "initialEase", text = it))
                        },
                        modifier = Modifier
                            .width(80.dp),
                        //label = { Text(text = "Current: ${settingsState.initialEase}")},
                        placeholder = { Text(text = settingsState.initialEase) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            }
            Column(
                modifier = Modifier.size(width = 350.dp, height = 200.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { onSettingsEvent(SettingsEvent.ApplySettings) },
                    modifier = Modifier.size(width = 350.dp, height = 60.dp)
                ) {
                    Box(modifier = Modifier) {
                        Text(
                            text = "Apply",
                            modifier = Modifier
                                .align(alignment = Alignment.Center),
                            fontSize = 36.sp,
                        )
                    }
                }
                Button(
                    onClick = { onSettingsEvent(SettingsEvent.ApplyDefaultSettings) },
                    modifier = Modifier.size(width = 350.dp, height = 60.dp)
                ) {
                    Box(modifier = Modifier) {
                        Text(
                            text = "Restore to Default",
                            modifier = Modifier
                                .align(alignment = Alignment.Center),
                            fontSize = 30.sp,
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(Modifier, rememberNavController(), { }, settingsState = SettingsState(), onSettingsEvent = { })
}