package com.example.kanjimemorized.ui.screens.library

import com.example.kanjimemorized.data.entities.Kanji

data class LibraryState(
    val kanji: List<Kanji> = emptyList(),
    val meaning: List<String> = emptyList(),
    val date: List<String?> = emptyList(),
    val sortType: SortType = SortType.UNICODE,
    )
