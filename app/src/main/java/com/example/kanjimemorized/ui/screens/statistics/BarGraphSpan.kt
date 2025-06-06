package com.example.kanjimemorized.ui.screens.statistics

enum class BarGraphSpan {
    WEEK1,
    MONTH1,
    MONTH3,
    YEAR1,
    ALL;

    override fun toString(): String {
        val string: String = when (this) {
            WEEK1 -> "Week"
            MONTH1 -> "Month"
            MONTH3 -> "3 Months"
            YEAR1 -> "Year"
            ALL -> "All"
        }
        return string
    }
}
