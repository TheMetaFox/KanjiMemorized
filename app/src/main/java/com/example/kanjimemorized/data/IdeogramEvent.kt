package com.example.kanjimemorized.data

sealed interface IdeogramEvent {
    object SaveIdeogram: IdeogramEvent
    data class SetUnicode(val unicode: String): IdeogramEvent
    data class SetMeanings(val meanings: String): IdeogramEvent
    data class SetStrokes(val strokes: String): IdeogramEvent
    object ShowDialog: IdeogramEvent
    object HideDialog: IdeogramEvent
    data class SortIdeograms(val sortType: SortType): IdeogramEvent
    data class DeleteIdeogram(val ideogram: Ideogram): IdeogramEvent
}