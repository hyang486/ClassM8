package com.example.scheduleapp.ui.register

import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.base.ViewState


data class RegisterViewState(
    val isEnable: Boolean = true,
    val isLoading: Boolean = false
) : ViewState

sealed interface RegisterViewEvent : ViewEvent {
    data object RouteHome : RegisterViewEvent
}