package com.example.kanjimemorized.ui.screens.settings

data class SettingsState(
    val dailyNewKanjiField: String = "",
    val dailyNewKanji: String = "",
    val initialEaseField: String = "",
    val initialEase: String = "",
    val retentionThresholdField: String = "",
    val retentionThreshold: String = "",
    val analyticsEnabledSwitch: Boolean = false,
    val analyticsEnabled: Boolean = false,
    val crashlyticsEnabledSwitch: Boolean = false,
    val crashlyticsEnabled: Boolean = false
)
