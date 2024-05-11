package com.example.kanjimemorized.ui.screens.library


sealed interface LibraryEvent {
    data class SortKanji(val sortType: SortType): LibraryEvent
    data class ToggleFilterNonStudyable(val filterNonStudyable: Boolean): LibraryEvent
    data object ResetKanji: LibraryEvent
}