package com.example.kanjimemorized.ui.screens.settings

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
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
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.data.entities.Settings
import com.example.kanjimemorized.ui.BottomNavBar
import com.example.kanjimemorized.ui.theme.KanjiMemorizedTheme

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
        },
        floatingActionButton = {
            Column(
                modifier = Modifier
                    .width(width = 350.dp)
                    .zIndex(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        onSettingsEvent(SettingsEvent.ApplySettings)
                        onSettingsEvent(SettingsEvent.UpdateButtons)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    enabled = settingsState.isDifferentFromCurrentSettings
                ) {
                    Text(
                        text = "Apply",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically),
                        fontSize = 32.sp,
                    )
                }
                Button(
                    onClick = {
                        onSettingsEvent(SettingsEvent.ApplyDefaultSettings)
                        onSettingsEvent(SettingsEvent.UpdateButtons)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    enabled = settingsState.isDifferentFromDefaultSettings
                ) {
                    Text(
                        text = "Restore to Default",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically),
                        fontSize = 26.sp
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(contentPadding)
                .zIndex(0f),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .width(350.dp)
                    .safeGesturesPadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                itemsIndexed(listOf<List<Any>>(
                    listOf(SettingType.DAILY_NEW_KANJI, "Limit of new kanji introduced each day", settingsState.dailyNewKanjiField),
                    listOf(SettingType.INITIAL_EASE, "Factor new a kanji's durability increases by", settingsState.initialEaseField),
                    listOf(SettingType.RETENTION_THRESHOLD, "Maximum retention set for kanji review", settingsState.retentionThresholdField),
                    listOf(SettingType.ANALYTICS_ENABLED, "Collects demographic, device info, & app usage data", settingsState.analyticsEnabledSwitch),
                    listOf(SettingType.CRASHLYTICS_ENABLED, "Collects device info & crash logs", settingsState.crashlyticsEnabledSwitch)
                )) { _, (settingType, description, value)  ->
                    SettingRow(
                        settingType = settingType as SettingType,
                        description = description as String,
                        value = value,
                        settingsState = settingsState,
                        onSettingsEvent = onSettingsEvent
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(height = 75.dp))
                }
//                listOf<List<Any>>(
//                    listOf(SettingType.DAILY_NEW_KANJI, "Limit of new kanji introduced each day", settingsState.dailyNewKanjiField),
//                    listOf(SettingType.INITIAL_EASE, "Factor new a kanji's durability increases by", settingsState.dailyNewKanjiField),
//                    listOf(SettingType.RETENTION_THRESHOLD, "Maximum retention set for kanji review", settingsState.dailyNewKanjiField),
//                    listOf(SettingType.ANALYTICS_ENABLED, "Collects demographic, device info, & app usage data", settingsState.dailyNewKanjiField),
//                    listOf(SettingType.CRASHLYTICS_ENABLED, "Collects device info & crash logs", settingsState.dailyNewKanjiField)
//                ).forEach { (settingType, description, value) ->
//                    SettingRow(
//                        settingType = settingType as SettingType,
//                        description = description as String,
//                        value = value as String,
//                        settingsState = settingsState,
//                        onSettingsEvent = onSettingsEvent
//                    )
//                }
                item {
//                    Column(
//                        modifier = Modifier.size(width = 350.dp, height = 200.dp),
//                        verticalArrangement = Arrangement.SpaceEvenly,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Button(
//                            onClick = {
//                                onSettingsEvent(SettingsEvent.ApplySettings)
//                                onSettingsEvent(SettingsEvent.UpdateButtons)
//                            },
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .wrapContentHeight(),
//                            enabled = settingsState.isDifferentFromCurrentSettings
//                        ) {
//                            Text(
//                                text = "Apply",
//                                modifier = Modifier
//                                    .align(alignment = Alignment.CenterVertically),
//                                fontSize = 32.sp,
//                            )
//
//                        }
//                        Button(
//                            onClick = {
//                                onSettingsEvent(SettingsEvent.ApplyDefaultSettings)
//                                onSettingsEvent(SettingsEvent.UpdateButtons)
//                            },
//                            modifier = Modifier.size(width = 350.dp, height = 60.dp),
//                            enabled = settingsState.isDifferentFromDefaultSettings
//                        ) {
//                            Box(modifier = Modifier) {
//                                Text(
//                                    text = "Restore to Default",
//                                    modifier = Modifier
//                                        .align(alignment = Alignment.Center),
//                                    fontSize = 26.sp,
//                                )
//                            }
//                        }
//                    }
                }
            }
        }
    }
}

@Composable
fun SettingRow(
    settingType: SettingType,
    description: String,
    value: Any,
    settingsState: SettingsState,
    onSettingsEvent: (SettingsEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.78f)
        ) {
            Box {
                Text(
                    text = settingType.toString(),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 24.sp,
                )
            }
            Box {
                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    lineHeight = 12.sp
                )
            }
        }
        when (settingType) {
            SettingType.DAILY_NEW_KANJI, SettingType.INITIAL_EASE, SettingType.RETENTION_THRESHOLD -> {
                TextField(
                    value = value as String,
                    onValueChange = {
                        onSettingsEvent(SettingsEvent.UpdateTextField(field = settingType, value = it))
                        onSettingsEvent(SettingsEvent.UpdateButtons)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
//                        .padding(2.dp),
                    placeholder = {
                        Text(
                            text = settingsState.settingsMap.getValue(settingType).setValue + if (settingType == SettingType.RETENTION_THRESHOLD) "%" else "",
                            fontSize = 16.sp,
                            letterSpacing = 0.070f.sp
                            ) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
            SettingType.ANALYTICS_ENABLED, SettingType.CRASHLYTICS_ENABLED -> {
                Switch(
                    checked = value as Boolean,
                    onCheckedChange = {
                        if (!it) {
                            onSettingsEvent(SettingsEvent.UpdateSwitch(switch = settingType, checked = false))
                            onSettingsEvent(SettingsEvent.UpdateButtons)
                        } else {
                            onSettingsEvent(SettingsEvent.ShowConfirmationDialog(setting = settingType))
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

val settingsMap: Map<SettingType, Settings> = mapOf(
    SettingType.DAILY_NEW_KANJI to Settings(code = SettingType.DAILY_NEW_KANJI, defaultValue = "3"),
    SettingType.INITIAL_EASE to Settings(code = SettingType.INITIAL_EASE, defaultValue = "2.5"),
    SettingType.RETENTION_THRESHOLD to Settings(code = SettingType.RETENTION_THRESHOLD, defaultValue = "80"),
    SettingType.ANALYTICS_ENABLED to Settings(code = SettingType.ANALYTICS_ENABLED, defaultValue = "false"),
    SettingType.CRASHLYTICS_ENABLED to Settings(code = SettingType.CRASHLYTICS_ENABLED, defaultValue = "false")
)

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreview_LightDark() {
    KanjiMemorizedTheme {
        SettingsScreen(modifier = Modifier, navController = rememberNavController(), settingsState = SettingsState( settingsMap = settingsMap), onSettingsEvent = { })
    }
}

@Preview(name = "85%", fontScale = 0.85f)
@Preview(name = "150%", fontScale = 1.5f)
@Preview(name = "200%", fontScale = 2f)
@Composable
fun SettingsScreenPreview_FontScale() {
    KanjiMemorizedTheme {
        SettingsScreen(modifier = Modifier, navController = rememberNavController(), settingsState = SettingsState( settingsMap = settingsMap), onSettingsEvent = { })
    }
}