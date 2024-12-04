package com.example.scheduleapp.ui.home

import com.example.scheduleapp.base.ViewEvent


sealed class HomeViewEvent : ViewEvent {
    data object Logout : HomeViewEvent()
    data object AddEvent : HomeViewEvent()
    data object OpenDrawer : HomeViewEvent()
    data object CloseDrawer : HomeViewEvent()
}


