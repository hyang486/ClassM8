package com.example.classm8.ui.event

import com.example.classm8.base.ViewEvent


sealed class AddEventViewEvent : ViewEvent {
    data object BackPress : AddEventViewEvent()
}

