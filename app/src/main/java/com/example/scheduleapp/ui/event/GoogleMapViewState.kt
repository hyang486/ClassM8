package com.example.scheduleapp.ui.event

import com.example.scheduleapp.base.ViewEvent
import com.google.android.gms.maps.model.LatLng



sealed interface GoogleMapViewEvent : ViewEvent {
    data object ZoomIn : GoogleMapViewEvent
    data object ZoomOut : GoogleMapViewEvent
    data class MoveCurrentLocation(val latLng: LatLng) : GoogleMapViewEvent
    data class Loading(val isVisible : Boolean) : GoogleMapViewEvent
}