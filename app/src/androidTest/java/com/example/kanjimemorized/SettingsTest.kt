package com.example.kanjimemorized

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemorized.data.entities.Settings
import com.example.kanjimemorized.ui.screens.settings.SettingType
import com.example.kanjimemorized.ui.screens.settings.SettingsScreen
import com.example.kanjimemorized.ui.screens.settings.SettingsState
import org.junit.Rule
import org.junit.Test

class SettingsTest {

    @get:Rule
    val rule: ComposeContentTestRule = createComposeRule()

    private val dropdownButton: SemanticsMatcher = hasContentDescription("more vertical") and hasClickAction()

    @Test
    fun clickDropdown_showsDropdown() {
        val settingsMap: Map<SettingType, Settings> = mapOf(
            SettingType.DAILY_NEW_KANJI to Settings(1, SettingType.DAILY_NEW_KANJI, "3", "3"),
            SettingType.INITIAL_EASE to Settings(2, SettingType.INITIAL_EASE, "2.5", "2.5"),
            SettingType.RETENTION_THRESHOLD to Settings(3, SettingType.RETENTION_THRESHOLD, "80", "80"),
            SettingType.ANALYTICS_ENABLED to Settings(4, SettingType.ANALYTICS_ENABLED, "false", "false"),
            SettingType.CRASHLYTICS_ENABLED to Settings(5, SettingType.CRASHLYTICS_ENABLED, "false", "false")
        )

        rule.setContent { SettingsScreen(Modifier, rememberNavController(), SettingsState(settingsMap = settingsMap)) { } }

        rule.onNode(matcher = dropdownButton).performClick()

        rule.onNodeWithText("App Guide").assertExists()

    }

}