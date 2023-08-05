package com.example.kanjimemorized.ui.screens.library

import com.example.kanjimemorized.data.Ideogram

sealed interface LibraryEvent {
    object SaveIdeogram: LibraryEvent
    data class SetUnicode(val unicode: String): LibraryEvent
    data class SetMeanings(val meanings: String): LibraryEvent
    data class SetStrokes(val strokes: String): LibraryEvent
    object ShowDialog: LibraryEvent
    object HideDialog: LibraryEvent
    data class SortIdeograms(val sortType: SortType): LibraryEvent
    data class DeleteIdeogram(val ideogram: Ideogram): LibraryEvent
}