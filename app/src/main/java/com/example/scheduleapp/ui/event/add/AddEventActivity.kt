package com.example.scheduleapp.ui.event.add

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.scheduleapp.R
import com.example.scheduleapp.ui.event.EventParentViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class AddEventActivity : AppCompatActivity(R.layout.activity_add_event) {
    private val eventParentViewModel by viewModels<EventParentViewModel>()
}