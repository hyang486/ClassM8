package com.example.scheduleapp.ui.event.update

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.scheduleapp.base.BaseViewModel
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.data.model.Event
import com.example.scheduleapp.usecase.AddEventUseCase
import com.example.scheduleapp.usecase.DeleteEventUseCase
import com.example.scheduleapp.util.DateUtil
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UpdateEventViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val addEventUseCase: AddEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
) : BaseViewModel() {

    private val _inputEventStateFlow = MutableStateFlow(Event())
    val inputEventStateFlow: StateFlow<Event> = _inputEventStateFlow.asStateFlow()

    private val getRouteEvent = savedStateHandle.get<Event>(ARG_SELECT_ITEM)

    init {
        getRouteEvent?.let { _inputEventStateFlow.value = it }
    }

    fun back(){
        onChangedViewEvent(UpdateEventViewEvent.BackPress)
    }

    fun delete() {
        getRouteEvent?.let {
            deleteEventUseCase(it).onEach { isSuccess ->
                if (isSuccess) {
                    onChangedViewEvent(ViewEvent.ShowToast("Delete Event!"))
                    onChangedViewEvent(UpdateEventViewEvent.BackPress)
                } else {
                    onChangedViewEvent(ViewEvent.ShowToast("Failed to Delete Event."))
                }
            }.launchIn(viewModelScope)
        }
    }

    fun edit() {
        if (_inputEventStateFlow.value.name.isEmpty()) {
            onChangedViewEvent(ViewEvent.ShowToast("Please enter the name"))
            return
        }

        if (_inputEventStateFlow.value.date.isEmpty()) {
            onChangedViewEvent(ViewEvent.ShowToast("Please enter the date"))
            return
        }

        if (_inputEventStateFlow.value.date.isEmpty()) {
            onChangedViewEvent(ViewEvent.ShowToast("Please enter the start time"))
            return
        }

        if (!DateUtil.isValidDateFormat(_inputEventStateFlow.value.date)) {
            onChangedViewEvent(ViewEvent.ShowToast("Please enter a valid date."))
            return
        }

        if (_inputEventStateFlow.value.startTime.isEmpty()) {
            onChangedViewEvent(ViewEvent.ShowToast("Please enter the start time"))
            return
        }

        if (!DateUtil.isValidTimeFormat(_inputEventStateFlow.value.startTime)) {
            onChangedViewEvent(ViewEvent.ShowToast("Please enter a valid start time."))
            return
        }

        if (_inputEventStateFlow.value.endTime.isEmpty()) {
            onChangedViewEvent(ViewEvent.ShowToast("Please enter the end time"))
            return
        }

        if (!DateUtil.isTimeLater(
                _inputEventStateFlow.value.endTime,
                _inputEventStateFlow.value.startTime
            )
        ) {
            onChangedViewEvent(ViewEvent.ShowToast("Please check the start time and end time again."))
            return
        }

        if (!DateUtil.isValidTimeFormat(_inputEventStateFlow.value.endTime)) {
            onChangedViewEvent(ViewEvent.ShowToast("Please enter a valid end time."))
            return
        }

        if (_inputEventStateFlow.value.detail.isEmpty()) {
            onChangedViewEvent(ViewEvent.ShowToast("Please enter the detail"))
            return
        }

        if (_inputEventStateFlow.value.latitude.toDouble() == 0.0 || _inputEventStateFlow.value.longitude.toDouble() == 0.0) {
            onChangedViewEvent(ViewEvent.ShowToast("Please enter the location"))
            return
        }


        getRouteEvent?.let {
            deleteEventUseCase(it).launchIn(viewModelScope)
        }


        addEventUseCase(_inputEventStateFlow.value).onEach { isSuccess ->
            if (isSuccess) {
                onChangedViewEvent(ViewEvent.ShowToast("Successfully Edit The Event."))
                onChangedViewEvent(UpdateEventViewEvent.BackPress)
            } else {
                onChangedViewEvent(ViewEvent.ShowToast("Failed to Edit Event."))
            }
        }.launchIn(viewModelScope)
    }

    fun routeGoogleMap() {
        onChangedViewEvent(UpdateEventViewEvent.RouteGoogleMap)
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

    fun updateLocation(latLng: LatLng) {
        _inputEventStateFlow.update { currentEvent ->
            currentEvent.copy(
                latitude = latLng.latitude.toString(),
                longitude = latLng.longitude.toString()
            )
        }
    }
}