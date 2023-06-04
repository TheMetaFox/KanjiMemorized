package com.example.kanjimemorized

data class IdeogramState(
    val ideograms: List<Ideogram> = emptyList(),
    val unicode: String = "",
    val meanings: String = "",
    val strokes: String = "",
    val isAddingIdeogram: Boolean = false,
    val sortType: SortType = SortType.UNICODE
)
