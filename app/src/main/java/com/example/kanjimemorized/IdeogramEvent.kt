package com.example.kanjimemorized

sealed interface IdeogramEvent {
    object SaveIdeogram: IdeogramEvent
    data class SetId(val id: Int): IdeogramEvent
    data class SetMeanings(val meanings: List<String>): IdeogramEvent
    data class SetStrokes(val strokes: Int): IdeogramEvent
    object ShowDialog: IdeogramEvent
    object HideDialog: IdeogramEvent
    data class SortIdeograms(val sortType: SortType): IdeogramEvent
    data class DeleteIdeogram(val ideogram: Ideogram): IdeogramEvent
}