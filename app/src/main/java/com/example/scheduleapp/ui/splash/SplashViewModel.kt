package com.example.scheduleapp.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.scheduleapp.base.BaseViewModel
import com.example.scheduleapp.usecase.CreateUserEventDBUseCase
import com.example.scheduleapp.usecase.GetCurrentFirebaseUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val currentFirebaseUserUseCase: GetCurrentFirebaseUserUseCase,
    private val createUserEventDBUseCase: CreateUserEventDBUseCase
) : BaseViewModel() {

    fun load() {
        viewModelScope.launch {
            val currentUser = currentFirebaseUserUseCase()

            if (currentUser != null) {
                delay(1000)
                createUserEventDBUseCase(currentUser.email.orEmpty()).map { isCreate ->
                    if (isCreate) {
                        onChangedViewState(SplashViewState.RouteHome)
                    } else {
                        onChangedViewState(SplashViewState.RouteLogin)
                    }
                }.first()
            } else {
                delay(1000)
                onChangedViewState(SplashViewState.RouteLogin)
            }
        }
    }
}