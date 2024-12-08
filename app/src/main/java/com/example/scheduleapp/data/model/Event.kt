package com.example.scheduleapp.data.model

data class Event(
    val name: String = "",
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val detail: String = "",
    val latitude: String = "0.0",
    val longitude: String = "0.0"
)
