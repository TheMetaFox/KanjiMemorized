package com.example.kanjimemorized.ui.screens.statistics

sealed interface StatisticsEvent {
//    data object ResetKanji: StatisticsEvent
    data object LoadStatisticsData: StatisticsEvent
    data class SetBarGraphSpan(val barGraphSpan: BarGraphSpan): StatisticsEvent

}