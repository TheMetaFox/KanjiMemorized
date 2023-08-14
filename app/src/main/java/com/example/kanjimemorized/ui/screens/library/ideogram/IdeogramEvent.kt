package com.example.kanjimemorized.ui.screens.library.ideogram

import com.example.kanjimemorized.data.Ideogram

sealed interface IdeogramEvent {
    data class DisplayIdeogramInfo(val ideogram: Ideogram): IdeogramEvent
}
