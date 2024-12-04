package com.example.scheduleapp.ui.login

import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.base.ViewState


data class LoginViewState(
    val isEnable: Boolean = true,
    val isLoading: Boolean = false
) : ViewState


sealed interface LoginViewEvent : ViewEvent {
    data object RouteRegister : LoginViewEvent
    data object RouteHome : LoginViewEvent
}