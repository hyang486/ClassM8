package com.example.scheduleapp.ui.event

import com.example.scheduleapp.base.BaseViewModel
import com.example.scheduleapp.data.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
) : BaseViewModel() {

    private val _inputEventStateFlow = MutableStateFlow(Event())
    val inputEventStateFlow: StateFlow<Event> = _inputEventStateFlow.asStateFlow()

    fun back() {
        onChangedViewEvent(AddEventViewEvent.BackPress)
    }

    fun add() {

    }

    fun updateName(name: String) {
        _inputEventStateFlow.update { currentEvent ->
            currentEvent.copy(
                name = name
            )
        }
    }


    fun updateDate(date: String) {
        _inputEventStateFlow.update { currentEvent ->
            currentEvent.copy(
                date = date
            )
        }
    }

    fun updateStartTime(startTime: String) {
        _inputEventStateFlow.update { currentEvent ->
            currentEvent.copy(
                startTime = startTime
            )
        }
    }

    fun updateEndTime(endTime: String) {
        _inputEventStateFlow.update { currentEvent ->
            currentEvent.copy(
                endTime = endTime
            )
        }
    }

    fun updateDetails(detail: String) {
        _inputEventStateFlow.update { currentEvent ->
            currentEvent.copy(
                detail = detail
            )
        }
    }
}
