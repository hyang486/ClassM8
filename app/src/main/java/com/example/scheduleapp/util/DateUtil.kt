package com.example.scheduleapp.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtil {

    fun getCurrentDateString() : String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return current.format(formatter)
    }

    fun isDateLater(date1: String, date2: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val parsedDate1 = LocalDate.parse(date1, formatter)
        val parsedDate2 = LocalDate.parse(date2, formatter)
        return parsedDate1.isAfter(parsedDate2)
    }
}