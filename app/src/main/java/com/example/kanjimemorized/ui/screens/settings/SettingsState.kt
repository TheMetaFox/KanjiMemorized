package com.example.kanjimemorized.ui.screens.settings

import com.example.kanjimemorized.data.entities.Settings

data class SettingsState(
    val dailyNewKanjiField: String = "",
    val initialEaseField: String = "",
    val retentionThresholdField: String = "",
    val analyticsEnabledSwitch: Boolean = false,
    val crashlyticsEnabledSwitch: Boolean = false,
    val isShowingConfirmationDialog: Boolean = false,
    val confirmationSetting: SettingType = SettingType.ANALYTICS_ENABLED,
    val isDifferentFromCurrentSettings: Boolean = false,
    val isDifferentFromDefaultSettings: Boolean = false,
    val settingsMap: Map<SettingType, Settings> = emptyMap()
)
