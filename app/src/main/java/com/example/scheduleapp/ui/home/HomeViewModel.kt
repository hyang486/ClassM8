package com.example.scheduleapp.ui.home

import com.example.scheduleapp.base.BaseViewModel
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.usecase.FirebaseLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseLogoutUseCase: FirebaseLogoutUseCase,
) : BaseViewModel() {

    fun logout() {
        if (firebaseLogoutUseCase()) {
            onChangedViewEvent(HomeViewEvent.Logout)
        } else {
            onChangedViewEvent(ViewEvent.ShowToast("로그아웃을 실패하였습니다."))
            onChangedViewEvent(HomeViewEvent.CloseDrawer)
        }
    }

    fun addEvent() {
        onChangedViewEvent(HomeViewEvent.AddEvent)
        onChangedViewEvent(HomeViewEvent.CloseDrawer)
    }

    fun openDrawer() {
        onChangedViewEvent(HomeViewEvent.OpenDrawer)
    }
}