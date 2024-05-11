package com.example.kanjimemorized.ui.screens.library.kanji

import com.example.kanjimemorized.data.entities.Kanji

sealed interface KanjiEvent {
    data class DisplayKanjiInfo(val kanji: Kanji): KanjiEvent
    data object ShowKanjiReviewData: KanjiEvent
    data object HideKanjiReviewData: KanjiEvent
}