package com.example.kanjimemorized.ui.screens.home

sealed interface HomeEvent {
    data object LoadHomeData: HomeEvent
}