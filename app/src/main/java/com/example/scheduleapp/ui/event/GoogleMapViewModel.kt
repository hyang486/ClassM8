package com.example.scheduleapp.ui.event

import androidx.lifecycle.viewModelScope
import com.example.scheduleapp.base.BaseViewModel
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.common.Result
import com.example.scheduleapp.ui.event.GoogleMapViewEvent
import com.example.scheduleapp.usecase.GetCurrentLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GoogleMapViewModel @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase
) : BaseViewModel() {


    fun zoomIn() {
        onChangedViewEvent(GoogleMapViewEvent.ZoomIn)
    }

    fun zoomOut() {
        onChangedViewEvent(GoogleMapViewEvent.ZoomOut)
    }

    fun moveCurrentLocation() {
        getCurrentLocationUseCase().onEach { result ->
            when (result) {
                is Result.Error -> {
                    onChangedViewEvent(GoogleMapViewEvent.Loading(false))
                    onChangedViewEvent(ViewEvent.ShowToast("Unable to move."))
                }
                Result.Loading -> {
                    onChangedViewEvent(GoogleMapViewEvent.Loading(true))
                }

                is Result.Success -> {
                    onChangedViewEvent(GoogleMapViewEvent.Loading(false))
                    onChangedViewEvent(GoogleMapViewEvent.MoveCurrentLocation(result.data))
                }
            }
        }.launchIn(viewModelScope)
    }
}