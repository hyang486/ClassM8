package com.example.scheduleapp.ui.event.add

import com.example.scheduleapp.base.ViewEvent


sealed class AddEventViewEvent : ViewEvent {
    data object BackPress : AddEventViewEvent()
    data object RouteGoogleMap : AddEventViewEvent()
}

