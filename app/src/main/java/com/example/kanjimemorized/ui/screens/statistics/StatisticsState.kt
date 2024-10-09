package com.example.kanjimemorized.ui.screens.statistics

data class StatisticsState(
    var unlocked: Int = 0,
    val unknown: Int = 0,
    val known: Int = 0,
    val mastered: Int = 0,
)
