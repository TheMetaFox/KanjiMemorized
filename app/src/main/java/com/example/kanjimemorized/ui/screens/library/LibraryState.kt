package com.example.kanjimemorized.ui.screens.library

import com.example.kanjimemorized.data.Ideogram

data class LibraryState(
    val ideograms: List<Ideogram> = emptyList(),
    val unicode: String = "",
    val meanings: String = "",
    val strokes: String = "",
    val isAddingIdeogram: Boolean = false,
    val sortType: SortType = SortType.UNICODE
)
