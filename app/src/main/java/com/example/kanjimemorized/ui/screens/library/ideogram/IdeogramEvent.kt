package com.example.kanjimemorized.ui.screens.library.ideogram

import com.example.kanjimemorized.data.Ideogram

sealed interface IdeogramEvent {
    data class DisplayIdeogram(val ideogram: Ideogram): IdeogramEvent
}
