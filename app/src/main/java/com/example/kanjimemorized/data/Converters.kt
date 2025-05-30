package com.example.kanjimemorized.data

import androidx.room.TypeConverter
import com.example.kanjimemorized.ui.screens.settings.SettingType

class Converters {
    @TypeConverter
    fun settingTypeToString(settingType: SettingType): String {
        return when (settingType) {
            SettingType.DAILY_NEW_KANJI -> "daily_new_kanji"
            SettingType.INITIAL_EASE -> "initial_ease"
            SettingType.RETENTION_THRESHOLD -> "retention_threshold"
            SettingType.ANALYTICS_ENABLED -> "analytics_enabled"
            SettingType.CRASHLYTICS_ENABLED -> "crashlytics_enabled"
        }
    }

    @TypeConverter
    fun stringToSettingType(settingCode: String): SettingType {
        return when (settingCode) {
            "daily_new_kanji" ->  SettingType.DAILY_NEW_KANJI
            "initial_ease" ->  SettingType.INITIAL_EASE
            "retention_threshold" ->  SettingType.RETENTION_THRESHOLD
            "analytics_enabled" ->  SettingType.ANALYTICS_ENABLED
            "crashlytics_enabled" ->  SettingType.CRASHLYTICS_ENABLED
            else -> SettingType.DAILY_NEW_KANJI
        }
    }
}