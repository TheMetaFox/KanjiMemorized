package com.example.kanjimemorized.ui.screens.settings

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kanjimemorized.data.KanjiRepository
import com.example.kanjimemorized.data.entities.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(private val kanjiRepository: KanjiRepository): ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true
    )
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(
        value = SettingsState()
    )

    val state: StateFlow<SettingsState> = _state

    fun onEvent(
        settingsEvent: SettingsEvent
    ) {
        when (settingsEvent) {
            is SettingsEvent.UpdateTextField -> {
                Log.i("SettingsViewModel.kt", "Updating text...")
                when (settingsEvent.field) {
                    SettingType.DAILY_NEW_KANJI -> {
                        _state.update {
                            it.copy(
                                dailyNewKanjiField = settingsEvent.value
                            )
                        }
                    }
                    SettingType.INITIAL_EASE -> {
                        _state.update {
                            it.copy(
                                initialEaseField = settingsEvent.value
                            )
                        }
                    }
                    SettingType.RETENTION_THRESHOLD -> {
                        _state.update {
                            it.copy(
                                retentionThresholdField = settingsEvent.value
                            )
                        }
                    }
                    else -> { }
                }
//                viewModelScope.launch {
//                    _state.update {
//                        it.copy(
//                            isDifferentFromCurrentSettings = !(
//                                    (state.value.dailyNewKanjiField == kanjiRepository.getSettingsFromCode(settingType = SettingType.DAILY_NEW_KANJI).setValue)
//                                    &&
//                                    (state.value.initialEaseField == kanjiRepository.getSettingsFromCode(settingType = SettingType.INITIAL_EASE).setValue)
//                                    &&
//                                    (state.value.retentionThresholdField == kanjiRepository.getSettingsFromCode(settingType = SettingType.RETENTION_THRESHOLD).setValue)
//                            ),
//                            isDifferentFromDefaultSettings = !(
//                                    (state.value.dailyNewKanjiField == kanjiRepository.getSettingsFromCode(settingType = SettingType.DAILY_NEW_KANJI).defaultValue)
//                                    &&
//                                    (state.value.initialEaseField == kanjiRepository.getSettingsFromCode(settingType = SettingType.INITIAL_EASE).defaultValue)
//                                    &&
//                                    (state.value.retentionThresholdField == kanjiRepository.getSettingsFromCode(settingType = SettingType.RETENTION_THRESHOLD).defaultValue)
//                            )
//                        )
//                    }
//                }
            }
            is SettingsEvent.UpdateSwitch -> {
                Log.i("SettingsViewModel.kt", "Updating switch...")
                when (settingsEvent.switch) {
                    SettingType.ANALYTICS_ENABLED -> {
                        _state.update {
                            it.copy(
                                analyticsEnabledSwitch = settingsEvent.checked
                            )
                        }
                    }
                    SettingType.CRASHLYTICS_ENABLED -> {
                        _state.update {
                            it.copy(
                                crashlyticsEnabledSwitch = settingsEvent.checked
                            )
                        }
                    }
                    else -> { }
                }
//                viewModelScope.launch {
//                    _state.update {
//                        it.copy(
//                            isDifferentFromCurrentSettings = !(
//                                    (state.value.analyticsEnabledSwitch == kanjiRepository.getSettingsFromCode(settingType = SettingType.ANALYTICS_ENABLED).setValue.toBooleanStrict())
//                                    &&
//                                    (state.value.crashlyticsEnabledSwitch == kanjiRepository.getSettingsFromCode(settingType = SettingType.CRASHLYTICS_ENABLED).setValue.toBooleanStrict())
//                            ),
//                            isDifferentFromDefaultSettings = !(
//                                    (state.value.analyticsEnabledSwitch == kanjiRepository.getSettingsFromCode(settingType = SettingType.ANALYTICS_ENABLED).defaultValue.toBooleanStrict())
//                                    &&
//                                    (state.value.crashlyticsEnabledSwitch == kanjiRepository.getSettingsFromCode(settingType = SettingType.CRASHLYTICS_ENABLED).defaultValue.toBooleanStrict())
//                            )
//                        )
//                    }
//                }
            }
            is SettingsEvent.UpdateButtons -> {
                Log.i("SettingsViewModel.kt", "Updating buttons...")
                Log.i("SettingsViewModel.kt", "Settings: ${state.value.settingsMap}...")
                var isDifferentFromCurrentSettings = false
                var isDifferentFromDefaultSettings = false
                state.value.settingsMap.forEach { (settingType, setting) ->
//                    Log.i("SettingsViewModel.kt", "Setting: ${setting.code}")
                    when (settingType) {
                        SettingType.DAILY_NEW_KANJI -> {
                            Log.i("SettingsViewModel.kt", "DAILY_NEW_KANJI:\t Field Value: ${state.value.dailyNewKanjiField}\t Set Value: ${setting.setValue} \tDefault Value: ${setting.defaultValue}...")
                            if (setting.setValue != state.value.dailyNewKanjiField && state.value.dailyNewKanjiField.isNotBlank()) {
                                isDifferentFromCurrentSettings = true
                                Log.i("SettingsViewModel.kt", "${settingType}:\t Apply Button Enabled...")
                            }
                            if (setting.setValue != state.value.settingsMap.getValue(SettingType.DAILY_NEW_KANJI).defaultValue) {
                                isDifferentFromDefaultSettings = true
                                Log.i("SettingsViewModel.kt", "${settingType}:\t Restore to Default Button Enabled...")
                            }
                        }
                        SettingType.INITIAL_EASE -> {
                            Log.i("SettingsViewModel.kt", "INITIAL_EASE:\t Field Value: ${state.value.initialEaseField}\t Set Value: ${setting.setValue} \tDefault Value: ${setting.defaultValue}...")
                            if (setting.setValue != state.value.initialEaseField && state.value.initialEaseField.isNotBlank()) {
                                isDifferentFromCurrentSettings = true
                                Log.i("SettingsViewModel.kt", "${settingType}:\t Apply Button Enabled...")
                            }
                            if (setting.setValue != state.value.settingsMap.getValue(SettingType.INITIAL_EASE).defaultValue) {
                                isDifferentFromDefaultSettings = true
                                Log.i("SettingsViewModel.kt", "${settingType}:\t Restore to Default Button Enabled...")
                            }
                        }
                        SettingType.RETENTION_THRESHOLD -> {
                            Log.i("SettingsViewModel.kt", "RETENTION_THRESHOLD:\t Field Value: ${state.value.retentionThresholdField}\t Set Value: ${setting.setValue} \tDefault Value: ${setting.defaultValue}...")
                            if (setting.setValue != state.value.retentionThresholdField && state.value.retentionThresholdField.isNotBlank()) {
                                isDifferentFromCurrentSettings = true
                                Log.i("SettingsViewModel.kt", "${settingType}:\t Apply Button Enabled...")
                            }
                            if (setting.setValue != state.value.settingsMap.getValue(SettingType.RETENTION_THRESHOLD).defaultValue) {
                                isDifferentFromDefaultSettings = true
                                Log.i("SettingsViewModel.kt", "${settingType}:\t Restore to Default Button Enabled...")
                            }
                        }
                        SettingType.ANALYTICS_ENABLED -> {
                            Log.i("SettingsViewModel.kt", "ANALYTICS_ENABLED:\t Field Value: ${state.value.analyticsEnabledSwitch}\t Set Value: ${setting.setValue} \tDefault Value: ${setting.defaultValue}...")
                            if (setting.setValue.toBooleanStrict() != state.value.analyticsEnabledSwitch) {
                                isDifferentFromCurrentSettings = true
                                Log.i("SettingsViewModel.kt", "${settingType}:\t Apply Button Enabled...")
                            }
                            if (setting.setValue != state.value.settingsMap.getValue(SettingType.ANALYTICS_ENABLED).defaultValue) {
                                isDifferentFromDefaultSettings = true
                                Log.i("SettingsViewModel.kt", "${settingType}:\t Restore to Default Button Enabled...")
                            }
                        }
                        SettingType.CRASHLYTICS_ENABLED -> {
                            Log.i("SettingsViewModel.kt", "CRASHLYTICS_ENABLED:\t Field Value: ${state.value.crashlyticsEnabledSwitch}\t Set Value: ${setting.setValue} \tDefault Value: ${setting.defaultValue}...")
                            if (setting.setValue.toBooleanStrict() != state.value.crashlyticsEnabledSwitch) {
                                isDifferentFromCurrentSettings = true
                                Log.i("SettingsViewModel.kt", "${settingType}:\t Apply Button Enabled...")
                            }
                            if (setting.setValue != state.value.settingsMap.getValue(SettingType.CRASHLYTICS_ENABLED).defaultValue) {
                                isDifferentFromDefaultSettings = true
                                Log.i("SettingsViewModel.kt", "${settingType}:\t Restore to Default Button Enabled...")
                            }
                        }
                    }
                }
                _state.update {
                    it.copy(
                        isDifferentFromCurrentSettings = isDifferentFromCurrentSettings,
                        isDifferentFromDefaultSettings = isDifferentFromDefaultSettings
                    )
                }
            }
            SettingsEvent.LoadSettingsData -> {
                Log.i("SettingsViewModel.kt", "Started loading initial settings data...")
                viewModelScope.launch {
                    val settingsMap: MutableMap<SettingType, Settings> = mutableMapOf()
                    kanjiRepository.getSettings().forEach { setting ->
                        settingsMap[setting.code] = setting
                    }
                    _state.update {
                        it.copy(
                            analyticsEnabledSwitch = kanjiRepository.getSettingsFromCode(settingType = SettingType.ANALYTICS_ENABLED).setValue.toBooleanStrict(),
                            crashlyticsEnabledSwitch = kanjiRepository.getSettingsFromCode(settingType = SettingType.CRASHLYTICS_ENABLED).setValue.toBooleanStrict(),
                            settingsMap = settingsMap
                        )
                    }
                    _isLoading.value = false
                    Log.i("SettingsViewModel.kt", "Finished loading initial settings data...")
                }
            }
            SettingsEvent.ApplySettings -> {
                Log.i("SettingsViewModel.kt", "Apply settings...")
                viewModelScope.launch {
                    val settingsMap: MutableMap<SettingType, Settings> = state.value.settingsMap.toMutableMap()

                    val dailyNewKanjiField: String = state.value.dailyNewKanjiField
                    if (dailyNewKanjiField.isNotBlank() && dailyNewKanjiField.isDigitsOnly() && dailyNewKanjiField.toInt() > 0) {
                        kanjiRepository.updateSettings(settingType = SettingType.DAILY_NEW_KANJI, setValue = dailyNewKanjiField)
                        settingsMap[SettingType.DAILY_NEW_KANJI] = settingsMap[SettingType.DAILY_NEW_KANJI]!!.copy(setValue = dailyNewKanjiField)
                    }

                    val initialEaseField: String = state.value.initialEaseField
                    if (initialEaseField.isNotBlank() && initialEaseField.count { it != '.' && !it.isDigit() } == 0 && initialEaseField.count { it.isDigit() } >= 1 && initialEaseField.count { it == '.' } <= 1) {
                        kanjiRepository.updateSettings(settingType = SettingType.INITIAL_EASE, setValue = "%.2f".format(initialEaseField.toFloat()))
                        settingsMap[SettingType.INITIAL_EASE] = settingsMap[SettingType.INITIAL_EASE]!!.copy(setValue = "%.2f".format(initialEaseField.toFloat()))
                    }

                    val retentionThresholdField: String = state.value.retentionThresholdField
                    if (retentionThresholdField.isNotBlank() && retentionThresholdField.isDigitsOnly() && retentionThresholdField.toInt() <= 99 && retentionThresholdField.toInt() >= 1) {
                        kanjiRepository.updateSettings(settingType = SettingType.RETENTION_THRESHOLD, setValue = retentionThresholdField)
                        settingsMap[SettingType.RETENTION_THRESHOLD] = settingsMap[SettingType.RETENTION_THRESHOLD]!!.copy(setValue = retentionThresholdField)
                    }

                    val analyticsEnabled: Boolean = state.value.analyticsEnabledSwitch
                    kanjiRepository.updateSettings(settingType = SettingType.ANALYTICS_ENABLED, setValue = analyticsEnabled.toString())
                    settingsMap[SettingType.ANALYTICS_ENABLED] = settingsMap[SettingType.ANALYTICS_ENABLED]!!.copy(setValue = analyticsEnabled.toString())

                    val crashlyticsEnabled: Boolean = state.value.crashlyticsEnabledSwitch
                    kanjiRepository.updateSettings(settingType = SettingType.CRASHLYTICS_ENABLED, setValue = crashlyticsEnabled.toString())
                    settingsMap[SettingType.CRASHLYTICS_ENABLED] = settingsMap[SettingType.CRASHLYTICS_ENABLED]!!.copy(setValue = crashlyticsEnabled.toString())

                    _state.update {
                        it.copy(
                            analyticsEnabledSwitch = kanjiRepository.getSettingsFromCode(settingType = SettingType.ANALYTICS_ENABLED).setValue.toBooleanStrict(),
                            crashlyticsEnabledSwitch = kanjiRepository.getSettingsFromCode(settingType = SettingType.CRASHLYTICS_ENABLED).setValue.toBooleanStrict(),
                            dailyNewKanjiField = "",
                            initialEaseField = "",
                            retentionThresholdField = "",
                            settingsMap = settingsMap
//                            isDifferentFromCurrentSettings = false
                        )
                    }
                }
            }
            SettingsEvent.ApplyDefaultSettings -> {
                Log.i("SettingsViewModel.kt", "Apply default settings...")
                val dailyNewKanjiDefault: String = state.value.settingsMap.getValue(SettingType.DAILY_NEW_KANJI).defaultValue
                val initialEaseDefault: String = state.value.settingsMap.getValue(SettingType.INITIAL_EASE).defaultValue
                val retentionThresholdDefault: String = state.value.settingsMap.getValue(SettingType.RETENTION_THRESHOLD).defaultValue
                val analyticsEnabledDefault: String = state.value.settingsMap.getValue(SettingType.ANALYTICS_ENABLED).defaultValue
                val crashlyticsEnabledDefault: String = state.value.settingsMap.getValue(SettingType.CRASHLYTICS_ENABLED).defaultValue
                viewModelScope.launch {
//                    val dailyNewKanjiDefault: String = kanjiRepository.getSettingsFromCode(settingType = SettingType.DAILY_NEW_KANJI).defaultValue
//                    val initialEaseDefault: String = kanjiRepository.getSettingsFromCode(settingType = SettingType.INITIAL_EASE).defaultValue
//                    val retentionThresholdDefault: String = kanjiRepository.getSettingsFromCode(settingType = SettingType.RETENTION_THRESHOLD).defaultValue
//                    val analyticsEnabledDefault: String = kanjiRepository.getSettingsFromCode(settingType = SettingType.ANALYTICS_ENABLED).defaultValue
//                    val crashlyticsEnabledDefault: String = kanjiRepository.getSettingsFromCode(settingType = SettingType.CRASHLYTICS_ENABLED).defaultValue

                    kanjiRepository.updateSettings(settingType = SettingType.DAILY_NEW_KANJI, setValue = dailyNewKanjiDefault)
                    kanjiRepository.updateSettings(settingType = SettingType.INITIAL_EASE, setValue = initialEaseDefault)
                    kanjiRepository.updateSettings(settingType = SettingType.RETENTION_THRESHOLD, setValue = retentionThresholdDefault)
                    kanjiRepository.updateSettings(settingType = SettingType.ANALYTICS_ENABLED, setValue = analyticsEnabledDefault)
                    kanjiRepository.updateSettings(settingType = SettingType.CRASHLYTICS_ENABLED, setValue = crashlyticsEnabledDefault)

                }
                _state.update {
                    it.copy(
                        dailyNewKanjiField = "",
                        initialEaseField = "",
                        retentionThresholdField = "",
                        analyticsEnabledSwitch = analyticsEnabledDefault.toBooleanStrict(),
                        crashlyticsEnabledSwitch = crashlyticsEnabledDefault.toBooleanStrict(),
                        settingsMap = state.value.settingsMap.mapValues { (_, setting) ->
                            setting.copy(setValue = setting.defaultValue)
                        }
                    )
                }
            }
            SettingsEvent.HideConfirmationDialog -> {
                _state.update {
                    it.copy(
                        isShowingConfirmationDialog = false
                    )
                }
            }
            is SettingsEvent.ShowConfirmationDialog -> {
                _state.update {
                    it.copy(
                        isShowingConfirmationDialog = true,
                        confirmationSetting = settingsEvent.setting
                    )
                }
            }
        }
    }
}