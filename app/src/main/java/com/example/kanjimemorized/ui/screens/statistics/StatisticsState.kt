package com.example.kanjimemorized.ui.screens.statistics

data class StatisticsState(
    val unlocked: Int = 0,
    val unknown: Int = 0,
    val known: Int = 0,
    val mastered: Int = 0,
    val dayForecastsMap: MutableMap<Int, Int> = mutableMapOf(),
    val barGraphSpan: BarGraphSpan = BarGraphSpan.WEEK1
)
enum class BarGraphSpan {
    WEEK1,
    MONTH1,
    MONTH3,
    YEAR1,
    ALL
}
