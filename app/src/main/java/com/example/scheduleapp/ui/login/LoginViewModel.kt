package com.example.scheduleapp.ui.login

import androidx.lifecycle.viewModelScope
import com.example.scheduleapp.base.BaseViewModel
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.usecase.CheckExistUserEventDBUseCase
import com.example.scheduleapp.usecase.CheckInputLogInUseCase
import com.example.scheduleapp.usecase.CheckLoginState
import com.example.scheduleapp.usecase.CreateUserEventDBUseCase
import com.example.scheduleapp.usecase.FirebaseLoginUseCase
import com.example.scheduleapp.usecase.LoginErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkInputLogInUseCase: CheckInputLogInUseCase,
    private val checkExistUserEventDBUseCase: CheckExistUserEventDBUseCase,
    private val createUserEventDBUseCase: CreateUserEventDBUseCase,
    private val firebaseLoginUseCase: FirebaseLoginUseCase
) : BaseViewModel() {

    val inputEmailStateFlow: MutableStateFlow<String?> = MutableStateFlow("")
    val inputPasswordStateFlow: MutableStateFlow<String?> = MutableStateFlow("")


    fun login() {
        when (val result = checkInputLogInUseCase(
            inputEmailStateFlow.value.orEmpty(),
            inputPasswordStateFlow.value.orEmpty()
        )) {
            is CheckLoginState.Failure -> {
                processLoginError(result.type)
            }

            CheckLoginState.Success -> {
                firebaseLoginUseCase(
                    inputEmailStateFlow.value.orEmpty(),
                    inputPasswordStateFlow.value.orEmpty()
                )
                    .onStart {
                        onChangedViewState(
                            LoginViewState(isEnable = false, isLoading = true)
                        )
                    }.map { isSuccessful ->
                        if (isSuccessful) {
                            createUserEventDBUseCase(inputEmailStateFlow.value.orEmpty()).map { isCreate ->
                                if (isCreate) {
                                    onChangedViewEvent(LoginViewEvent.RouteHome)
                                } else {
                                    onChangedViewEvent(ViewEvent.ShowToast("Login Failed."))
                                    onChangedViewState(
                                        LoginViewState(isEnable = true, isLoading = false)
                                    )
                                }
                            }.first()
                        } else {
                            onChangedViewEvent(ViewEvent.ShowToast("The login attempt has failed."))
                            onChangedViewState(
                                LoginViewState(isEnable = true, isLoading = false)
                            )
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }


    private fun processLoginError(type: LoginErrorType) {
        viewModelScope.launch {
            when (type) {
                LoginErrorType.NotInputEmail -> {
                    onChangedViewEvent(ViewEvent.ShowToast("Please enter your email."))
                }

                LoginErrorType.NotInputPassword -> {
                    onChangedViewEvent(ViewEvent.ShowToast("Please enter your password."))
                }

                LoginErrorType.InvalidEmail -> {
                    onChangedViewEvent(ViewEvent.ShowToast("The email format is incorrect."))
                }
            }
        }
    }


    fun register() {
        onChangedViewEvent(LoginViewEvent.RouteRegister)
    }
}