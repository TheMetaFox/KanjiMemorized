package com.example.kanjimemorized.ui

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Study: Screen(route = "study_screen")
    object StudyPlayground: Screen(route = "study_playground_screen")
    object Flashcard: Screen(route = "flashcard_screen")
    object Library: Screen(route = "library_screen")
    object Kanji: Screen(route = "kanji_screen")
}
