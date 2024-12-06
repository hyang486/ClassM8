package com.example.classm8.ui.map

import com.example.classm8.base.ViewEvent
import com.example.classm8.base.ViewState
import com.google.android.gms.maps.model.LatLng

sealed interface GoogleMapViewState : ViewState


sealed interface GoogleMapViewEvent : ViewEvent {
    data object ZoomIn : GoogleMapViewEvent
    data object ZoomOut : GoogleMapViewEvent
    data class MoveCurrentLocation(val latLng: LatLng) : GoogleMapViewEvent
    data class Loading(val isVisible : Boolean) : GoogleMapViewEvent
}