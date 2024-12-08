package com.example.scheduleapp.ui.home

import com.example.scheduleapp.base.ViewEvent

import com.example.scheduleapp.base.ViewState
import com.example.scheduleapp.data.model.Event
data class HomeViewState(
    val currentEvents : List<Event> = emptyList(),
    val upcomingEvents : List<Event> = emptyList(),
) : ViewState


sealed class HomeViewEvent : ViewEvent {
    data object Logout : HomeViewEvent()
    data object AddEvent : HomeViewEvent()
    data object OpenDrawer : HomeViewEvent()
    data object CloseDrawer : HomeViewEvent()
}


