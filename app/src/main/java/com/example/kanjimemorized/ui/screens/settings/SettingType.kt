package com.example.kanjimemorized.ui.screens.settings

enum class SettingType {
    DAILY_NEW_KANJI,
    INITIAL_EASE,
    RETENTION_THRESHOLD,
    ANALYTICS_ENABLED,
    CRASHLYTICS_ENABLED;

    override fun toString(): String {
        val string: String = when (this) {
            DAILY_NEW_KANJI -> "Daily New Kanji"
            INITIAL_EASE -> "Initial Ease"
            RETENTION_THRESHOLD -> "Retention Threshold"
            ANALYTICS_ENABLED -> "Analytics Enabled"
            CRASHLYTICS_ENABLED -> "Crashlytics Enabled"
        }
        return string
    }
}