package com.example.kanjimemorized.ui.screens.settings

sealed interface SettingsEvent {
    data class UpdateTextField(val field: String, val value: String): SettingsEvent
    data class UpdateSwitch(val switch: String, val checked: Boolean): SettingsEvent
    data object LoadSettingsData: SettingsEvent
    data object ApplySettings: SettingsEvent
    data object ApplyDefaultSettings: SettingsEvent
}