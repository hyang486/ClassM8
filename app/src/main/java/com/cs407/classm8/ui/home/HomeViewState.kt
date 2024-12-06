package com.example.classm8.ui.home

import com.example.classm8.base.ViewEvent


sealed class HomeViewEvent : ViewEvent {
    data object Logout : HomeViewEvent()
    data object AddEvent : HomeViewEvent()
    data object OpenDrawer : HomeViewEvent()
    data object CloseDrawer : HomeViewEvent()
}


