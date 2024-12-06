package com.example.classm8.ui.login

import com.example.classm8.base.ViewEvent
import com.example.classm8.base.ViewState


data class LoginViewState(
    val isEnable: Boolean = true,
    val isLoading: Boolean = false
) : ViewState


sealed interface LoginViewEvent : ViewEvent {
    data object RouteRegister : LoginViewEvent
    data object RouteHome : LoginViewEvent
}