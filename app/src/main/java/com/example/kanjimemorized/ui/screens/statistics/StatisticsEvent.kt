package com.example.kanjimemorized.ui.screens.statistics

import com.example.kanjimemorized.ui.screens.library.SortType

sealed interface StatisticsEvent {
//    data object ResetKanji: StatisticsEvent
    data object LoadStatistics: StatisticsEvent
}