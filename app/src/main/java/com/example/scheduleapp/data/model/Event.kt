package com.example.scheduleapp.data.model

import android.os.Parcelable
import androidx.core.text.HtmlCompat
import com.example.scheduleapp.util.DateUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
    val name: String = "",
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val detail: String = "",
    val latitude: String = "0.0",
    val longitude: String = "0.0"
) : Parcelable {
    fun startToEndTime(): String {
        return "$startTime - $endTime"
    }

    fun upComingTitle(): CharSequence {
        val boldPart = "· $name"
        val normalPart = " ${DateUtil.convertUpcomingDate(date)}"
        return HtmlCompat.fromHtml("<b>$boldPart</b>$normalPart", HtmlCompat.FROM_HTML_MODE_COMPACT)
    }
}
