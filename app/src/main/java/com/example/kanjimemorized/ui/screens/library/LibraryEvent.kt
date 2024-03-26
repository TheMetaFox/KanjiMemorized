package com.example.kanjimemorized.ui.screens.library

import com.example.kanjimemorized.data.entities.Kanji

sealed interface LibraryEvent {
    data class SortKanji(val sortType: SortType): LibraryEvent
    data class ToggleFilterNonStudyable(val filterNonStudyable: Boolean): LibraryEvent
    data class GetRetentionFromKanji(val kanji: Char): LibraryEvent
    data object ResetKanji: LibraryEvent
}