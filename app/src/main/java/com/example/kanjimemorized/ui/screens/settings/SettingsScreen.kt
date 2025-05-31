package com.example.kanjimemorized.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.ui.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier,
    navController: NavHostController,
    settingsState: SettingsState,
    onSettingsEvent: (SettingsEvent) -> Unit,
) {
    onSettingsEvent(SettingsEvent.UpdateButtons)
    if (settingsState.isShowingConfirmationDialog) {
        ConfirmationDialog(
            onSettingsEvent = onSettingsEvent,
            settingsState = settingsState
        )
    }
    Scaffold(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(insets = WindowInsets.statusBars)
            .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                actions = {
                    var showDropDownMenu by remember { mutableStateOf(false) }
                    IconButton(
                        onClick = { showDropDownMenu = true }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = "more vertical"
                        )
                    }
                    val localUriHandler: UriHandler = LocalUriHandler.current
                    DropdownMenu(
                        expanded = showDropDownMenu,
                        onDismissRequest = { showDropDownMenu = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text("App Guide") },
                            onClick = {
                                showDropDownMenu = false
                                localUriHandler.openUri("https://github.com/TheMetaFox/KanjiMemorized?tab=readme-ov-file#app-guide")
                            },
                            leadingIcon = { Icon(Icons.Outlined.Info, "info") }
                        )
                        DropdownMenuItem(
                            text = { Text("Feedback") },
                            onClick = {
                                showDropDownMenu = false
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
            BottomNavBar(selected = "Settings", navController = navController)
        }
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                            onSettingsEvent(SettingsEvent.UpdateTextField(field = SettingType.DAILY_NEW_KANJI, value = it))
                            onSettingsEvent(SettingsEvent.UpdateButtons)
                        },
                        modifier = Modifier
                            .width(80.dp),
                        placeholder = { Text(text = settingsState.settingsMap.getValue(SettingType.DAILY_NEW_KANJI).setValue) },
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
                                text = "Factor new a kanji's durability increases by",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                    TextField(
                        value = settingsState.initialEaseField,
                        onValueChange = {
                            onSettingsEvent(SettingsEvent.UpdateTextField(field = SettingType.INITIAL_EASE, value = it))
                            onSettingsEvent(SettingsEvent.UpdateButtons)
                        },
                        modifier = Modifier
                            .width(80.dp),
                        placeholder = { Text(text = settingsState.settingsMap.getValue(SettingType.INITIAL_EASE).setValue.trimEnd { it == '0' }.trimEnd { it == '.'}) },
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
                                text = "Retention Threshold",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 24.sp,
                            )
                        }
                        Box {
                            Text(
                                text = "Maximum retention set for kanji review",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                    TextField(
                        value = settingsState.retentionThresholdField,
                        onValueChange = {
                            onSettingsEvent(SettingsEvent.UpdateTextField(field = SettingType.RETENTION_THRESHOLD, value = it))
                            onSettingsEvent(SettingsEvent.UpdateButtons)
                        },
                        modifier = Modifier
                            .width(80.dp),
                        placeholder = { Text(text = "${settingsState.settingsMap.getValue(SettingType.RETENTION_THRESHOLD).setValue}%") },
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
                                text = "Analytics Enabled",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 24.sp,
                            )
                        }
                        Box {
                            Text(
                                text = "Collects demographic, device info, & app usage data",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                    Switch(
                        checked = settingsState.analyticsEnabledSwitch,
                        onCheckedChange = {
//                            Log.i("SettingsScreen.kt", "Checked changed...")
//                            onSettingsEvent(SettingsEvent.UpdateSwitch(switch = "analyticsEnabled", checked = it))
                            if (!it) {
                                onSettingsEvent(SettingsEvent.UpdateSwitch(switch = SettingType.ANALYTICS_ENABLED, checked = false))
                                onSettingsEvent(SettingsEvent.UpdateButtons)
                            } else {
                                onSettingsEvent(SettingsEvent.ShowConfirmationDialog(setting = SettingType.ANALYTICS_ENABLED))
                            }
                        }
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
                                text = "Crashlytics Enabled",
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 24.sp,
                            )
                        }
                        Box {
                            Text(
                                text = "Collects device info & crash logs",
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                    Switch(
                        checked = settingsState.crashlyticsEnabledSwitch,
                        onCheckedChange = {
//                            Log.i("SettingsScreen.kt", "Checked changed...")
//                            onSettingsEvent(SettingsEvent.UpdateSwitch(switch = "crashlyticsEnabled", checked = it))
                            if (!it) {
                                onSettingsEvent(SettingsEvent.UpdateSwitch(switch = SettingType.CRASHLYTICS_ENABLED, checked = false))
                                onSettingsEvent(SettingsEvent.UpdateButtons)
                            } else {
                                onSettingsEvent(SettingsEvent.ShowConfirmationDialog(setting = SettingType.CRASHLYTICS_ENABLED))
                            }
                        }
                    )
                }
            }
            Column(
                modifier = Modifier.size(width = 350.dp, height = 200.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        onSettingsEvent(SettingsEvent.ApplySettings)
                        onSettingsEvent(SettingsEvent.UpdateButtons)
                    },
                    modifier = Modifier.size(width = 350.dp, height = 60.dp),
                    enabled = settingsState.isDifferentFromCurrentSettings
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
                    onClick = {
                        onSettingsEvent(SettingsEvent.ApplyDefaultSettings)
                        onSettingsEvent(SettingsEvent.UpdateButtons)
                              },
                    modifier = Modifier.size(width = 350.dp, height = 60.dp),
                    enabled = settingsState.isDifferentFromDefaultSettings
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
    SettingsScreen(modifier = Modifier, navController = rememberNavController(), settingsState = SettingsState(), onSettingsEvent = { })
}