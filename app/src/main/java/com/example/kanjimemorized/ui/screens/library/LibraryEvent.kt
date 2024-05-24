package com.example.kanjimemorized.ui.screens.library


sealed interface LibraryEvent {
    data class SortKanji(val sortType: SortType): LibraryEvent
    data object ResetKanji: LibraryEvent
}