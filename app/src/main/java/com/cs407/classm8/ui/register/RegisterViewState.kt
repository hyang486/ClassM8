package com.example.classm8.ui.register

import com.example.classm8.base.ViewEvent
import com.example.classm8.base.ViewState


data class RegisterViewState(
    val isEnable: Boolean = true,
    val isLoading: Boolean = false
) : ViewState

sealed interface RegisterViewEvent : ViewEvent {
    data object RouteHome : RegisterViewEvent
}