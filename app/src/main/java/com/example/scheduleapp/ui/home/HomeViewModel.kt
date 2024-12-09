package com.example.scheduleapp.ui.home

import com.example.scheduleapp.base.BaseViewModel
import com.example.scheduleapp.base.ViewEvent
import com.example.scheduleapp.usecase.FirebaseLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.scheduleapp.data.model.Event
import com.example.scheduleapp.usecase.GetUserEventUseCase
import com.example.scheduleapp.util.DateUtil.getCurrentDateString
import com.example.scheduleapp.util.DateUtil.isDateLater
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseLogoutUseCase: FirebaseLogoutUseCase,
    getUserEventUseCase: GetUserEventUseCase,
) : BaseViewModel() {

    init {
        getUserEventUseCase()
            .map(::toHomeViewState)
            .onEach(::onChangedViewState)
            .launchIn(viewModelScope)
    }

    fun logout() {
        if (firebaseLogoutUseCase()) {
            onChangedViewEvent(HomeViewEvent.Logout)
        } else {
            onChangedViewEvent(ViewEvent.ShowToast("faild on login"))
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

    private fun toHomeViewState(list: List<Event>): HomeViewState =
        HomeViewState(
            currentEvents = list.filter { it.date == getCurrentDateString() },
            upcomingEvents = list.filter { isDateLater(it.date, getCurrentDateString()) }
                .sortedBy { it.date }.take(5)
        )
}