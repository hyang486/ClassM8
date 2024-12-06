package com.example.classm8.ui.splash

import com.example.classm8.base.ViewState

sealed interface SplashViewState : ViewState {
    data object RouteLogin : SplashViewState
    data object RouteHome : SplashViewState
}
