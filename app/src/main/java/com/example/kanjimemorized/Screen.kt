package com.example.kanjimemorized

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Study: Screen(route = "study_screen")
    object Ideogram: Screen(route = "ideogram_screen")
}
