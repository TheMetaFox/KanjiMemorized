package com.example.kanjimemorized.ui.screens.settings

sealed interface SettingsEvent {
    data class UpdateTextField(val field: String, val text: String): SettingsEvent
    data object LoadSettingsData: SettingsEvent
    data object ApplySettings: SettingsEvent
    data object ApplyDefaultSettings: SettingsEvent
}