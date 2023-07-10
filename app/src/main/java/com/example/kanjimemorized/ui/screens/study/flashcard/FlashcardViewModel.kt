package com.example.kanjimemorized.ui.screens.study.flashcard

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.kanjimemorized.data.IdeogramRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FlashcardViewModel(private val ideogramRepository: IdeogramRepository): ViewModel() {

    private val _state: MutableStateFlow<FlashcardState> = MutableStateFlow(
        value = FlashcardState()
    )

    val state: StateFlow<FlashcardState> = _state
}