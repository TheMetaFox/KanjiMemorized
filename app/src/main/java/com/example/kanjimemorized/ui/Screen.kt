package com.example.kanjimemorized.ui

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home_screen")
    data object Study: Screen(route = "study_screen")
    data object StudyPlayground: Screen(route = "study_playground_screen")
    data object Learn: Screen(route = "learn_screen")
    data object Review: Screen(route = "review_screen")
    data object Flashcard: Screen(route = "flashcard_screen")
    data object Library: Screen(route = "library_screen")
    data object Kanji: Screen(route = "kanji_screen")
    data object Statistics: Screen(route = "statistics_screen")
    data object Settings: Screen(route = "settings_screen")
}
