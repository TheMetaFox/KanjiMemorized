package com.example.kanjimemorized.ui.screens.library

import com.example.kanjimemorized.data.entities.Kanji
import java.time.LocalDateTime

data class LibraryState(
    val kanji: List<Kanji> = emptyList(),
    val date: List<LocalDateTime> = emptyList(),
    val filterNonStudyable: Boolean = false,
    val sortType: SortType = SortType.UNICODE,
    )
