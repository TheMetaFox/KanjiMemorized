package com.example.kanjimemorized.ui.screens.settings

sealed interface SettingsEvent {
    data class UpdateTextField(val field: SettingType, val value: String): SettingsEvent
    data class UpdateSwitch(val switch: SettingType, val checked: Boolean): SettingsEvent
    data object UpdateButtons: SettingsEvent
    data object LoadSettingsData: SettingsEvent
    data object ApplySettings: SettingsEvent
    data object ApplyDefaultSettings: SettingsEvent
    data class ShowConfirmationDialog(val setting: SettingType) : SettingsEvent
    data object HideConfirmationDialog : SettingsEvent
}