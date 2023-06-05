package com.example.kanjimemorized.ui

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Study: Screen(route = "study_screen")
    object Ideogram: Screen(route = "ideogram_screen")
}
