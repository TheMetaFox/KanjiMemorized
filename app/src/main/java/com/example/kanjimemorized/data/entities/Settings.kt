package com.example.kanjimemorized.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kanjimemorized.ui.screens.settings.SettingType

@Entity
data class Settings(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val code: SettingType,
    val setValue: String,
    val defaultValue: String,
) {
    constructor(code: SettingType, defaultValue: String) : this(
        id = 0, code = code, setValue = defaultValue, defaultValue = defaultValue
    )
    constructor(code: SettingType, setValue: String, defaultValue: String) : this(
        id = 0, code = code, setValue = setValue, defaultValue = defaultValue
    )
}
