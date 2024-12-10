package com.example.scheduleapp.ui.event

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.scheduleapp.data.model.Event
import com.example.scheduleapp.ui.event.update.ARG_SELECT_ITEM
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
@HiltViewModel
class EventParentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _saveLatLngStateFlow = MutableStateFlow(LatLng(0.0, 0.0))
    val saveLatLngStateFlow: StateFlow<LatLng> = _saveLatLngStateFlow.asStateFlow()
    init {
        savedStateHandle.get<Event>(ARG_SELECT_ITEM)?.let {
            _saveLatLngStateFlow.value = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
        }
    }
    fun saveLatLng(latLng: LatLng) {
        _saveLatLngStateFlow.value = latLng
    }
}