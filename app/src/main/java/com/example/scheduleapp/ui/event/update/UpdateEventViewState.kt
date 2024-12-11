package com.example.scheduleapp.ui.event.update

import com.example.scheduleapp.base.ViewEvent

sealed class UpdateEventViewEvent : ViewEvent {
    data object BackPress : UpdateEventViewEvent()
    data object RouteGoogleMap : UpdateEventViewEvent()
}