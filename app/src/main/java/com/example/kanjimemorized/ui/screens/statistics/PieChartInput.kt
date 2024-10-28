package com.example.kanjimemorized.ui.screens.statistics

import androidx.compose.ui.graphics.Color

data class PieChartInput(
    val value: Float,
    val color: Color,
    val label: String,
    val isSelected: Boolean = false
)