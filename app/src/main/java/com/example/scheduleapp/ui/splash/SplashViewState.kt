package com.example.scheduleapp.ui.splash

import com.example.scheduleapp.base.ViewState

sealed interface SplashViewState : ViewState {
    data object RouteLogin : SplashViewState
    data object RouteHome : SplashViewState
}
