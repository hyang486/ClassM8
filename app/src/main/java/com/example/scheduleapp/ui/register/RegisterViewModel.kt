package com.example.scheduleapp.ui.register

import androidx.lifecycle.viewModelScope
import com.example.scheduleapp.base.BaseViewModel
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.ui.login.LoginViewEvent
import com.example.scheduleapp.ui.login.LoginViewState
import com.example.scheduleapp.usecase.CheckInputRegisterUseCase
import com.example.scheduleapp.usecase.CheckRegisterState
import com.example.scheduleapp.usecase.CreateUserEventDBUseCase
import com.example.scheduleapp.usecase.FirebaseRegisterUseCase
import com.example.scheduleapp.usecase.RegisterErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val checkInputRegisterUseCase: CheckInputRegisterUseCase,
    private val firebaseRegisterUseCase: FirebaseRegisterUseCase,
    private val createUserEventDBUseCase: CreateUserEventDBUseCase
) : BaseViewModel() {


    val inputEmailStateFlow: MutableStateFlow<String?> = MutableStateFlow("")
    val inputPasswordStateFlow: MutableStateFlow<String?> = MutableStateFlow("")
    val inputPasswordOkStateFlow: MutableStateFlow<String?> = MutableStateFlow("")

    fun register() {

        when (val result = checkInputRegisterUseCase(
            inputEmailStateFlow.value.orEmpty(),
            inputPasswordStateFlow.value.orEmpty(),
            inputPasswordOkStateFlow.value.orEmpty()
        )) {
            is CheckRegisterState.Failure -> {
                processRegisterError(result.type)
            }

            CheckRegisterState.Success -> {
                firebaseRegisterUseCase(
                    inputEmailStateFlow.value.orEmpty(),
                    inputPasswordStateFlow.value.orEmpty()
                ).onStart {
                    onChangedViewState(
                        RegisterViewState(
                            isEnable = false,
                            isLoading = true
                        )
                    )
                }.map { isSuccessful ->
                    if (isSuccessful) {
                        createUserEventDBUseCase(inputEmailStateFlow.value.orEmpty()).map { isCreate ->
                            if (isCreate) {
                                onChangedViewEvent(RegisterViewEvent.RouteHome)
                            } else {
                                onChangedViewEvent(ViewEvent.ShowToast("Login failed."))
                                onChangedViewState(
                                    RegisterViewState(isEnable = true, isLoading = false)
                                )
                            }
                        }.first()
                    } else {
                        onChangedViewEvent(ViewEvent.ShowToast("Sign-up failed."))
                        onChangedViewState(
                            RegisterViewState(isEnable = true, isLoading = false)
                        )
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun processRegisterError(type: RegisterErrorType) {
        viewModelScope.launch {
            when (type) {
                RegisterErrorType.NotInputEmail -> {
                    onChangedViewEvent(ViewEvent.ShowToast("Please enter your email."))
                }

                RegisterErrorType.NotInputPassword -> {
                    onChangedViewEvent(ViewEvent.ShowToast("Please enter your password."))
                }

                RegisterErrorType.NotMatchPasswordOk -> {
                    onChangedViewEvent(ViewEvent.ShowToast("Please re-enter the password correctly."))
                }
            }
        }
    }
}