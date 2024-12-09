package com.example.scheduleapp.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.LocalTime
import java.util.Locale

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

    fun isValidDateFormat(dateString: String): Boolean {
        return try {
            val regex = """^\d{4}\.(0[1-9]|1[0-2])\.(0[1-9]|[12]\d|3[01])$""".toRegex()
            if (!regex.matches(dateString)) return false
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
            LocalDate.parse(dateString, formatter)
            true
        } catch (e: Exception) {
            false
        }
    }
    fun isValidTimeFormat(timeString: String): Boolean {
        return try {
            val regex = """^([01]\d|2[0-3]):([0-5]\d)$""".toRegex()
            if (!regex.matches(timeString)) return false
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            LocalTime.parse(timeString, formatter)
            true
        } catch (e: Exception) {
            false
        }
    }
    fun isTimeLater(time1: String, time2: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val parsedTime1 = LocalTime.parse(time1, formatter)
        val parsedTime2 = LocalTime.parse(time2, formatter)
        return parsedTime1.isAfter(parsedTime2)
    }
    fun convertUpcomingDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val outputFormatter = DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH)
        return LocalDate.parse(dateString, inputFormatter).format(outputFormatter)
    }
}