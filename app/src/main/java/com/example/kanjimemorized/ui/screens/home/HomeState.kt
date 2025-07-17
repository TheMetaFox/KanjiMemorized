package com.example.kanjimemorized.ui.screens.home

import java.time.LocalDate

data class HomeState(
    val projectedCompletionDate: LocalDate = LocalDate.now(),
    val currentReviewCount: Int = 0,
    val currentNewCount: Int = 0,
)
