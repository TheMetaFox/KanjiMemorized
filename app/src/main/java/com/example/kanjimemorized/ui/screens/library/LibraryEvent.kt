package com.example.kanjimemorized.ui.screens.library

import com.example.kanjimemorized.data.Ideogram

sealed interface LibraryEvent {
    data class SortIdeograms(val sortType: SortType): LibraryEvent
}