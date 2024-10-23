package com.example.kanjimemorized.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Settings(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val code: String,
    val setValue: String,
    val defaultValue: String,
) {
    constructor(code: String, defaultValue: String) : this(
        id = 0, code = code, setValue = defaultValue, defaultValue = defaultValue
    )
    constructor(code: String, setValue: String, defaultValue: String) : this(
        id = 0, code = code, setValue = setValue, defaultValue = defaultValue
    )
}
