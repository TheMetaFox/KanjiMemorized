package com.example.kanjimemorized.ui.screens.home

import com.example.kanjimemorized.ui.screens.library.SortType

sealed interface HomeEvent {
    data object LoadHomeData: HomeEvent
}