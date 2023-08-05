package com.example.kanjimemorized.ui.screens.library.ideogram

import androidx.lifecycle.ViewModel
import com.example.kanjimemorized.data.IdeogramRepository
import com.example.kanjimemorized.ui.screens.study.flashcard.FlashcardEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class IdeogramViewModel(private val ideogramRepository: IdeogramRepository): ViewModel() {

    private val _state: MutableStateFlow<IdeogramState> = MutableStateFlow(
        value = IdeogramState()
    )

    val state: StateFlow<IdeogramState> = _state

    fun onEvent(
        ideogramEvent: IdeogramEvent
    ) {
        when(ideogramEvent) {
            is IdeogramEvent.DisplayIdeogram -> {
                _state.update(
                    function = {
                        it.copy(
                            ideogram = ideogramEvent.ideogram
                        )
                    }
                )
            }
        }
    }
}