package com.example.kanjimemorized.ui.screens.statistics

import com.example.kanjimemorized.ui.screens.library.LibraryEvent
import com.example.kanjimemorized.ui.screens.library.SortType

sealed interface StatisticsEvent {
//    data object ResetKanji: StatisticsEvent
    data object LoadStatisticsData: StatisticsEvent
    data class SetBarGraphSpan(val barGraphSpan: BarGraphSpan): StatisticsEvent

}