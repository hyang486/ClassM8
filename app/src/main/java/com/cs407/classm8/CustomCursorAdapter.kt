package com.cs407.classm8

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

class CustomCursorAdapter(context: Context, cursor: Cursor) :
    CursorAdapter(context, cursor, 0) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        // 레이아웃을 생성하고 반환합니다.
        return LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val eventName = view.findViewById<TextView>(R.id.event_name)
        val eventStartTime = view.findViewById<TextView>(R.id.event_start_time)
        val eventEndTime = view.findViewById<TextView>(R.id.event_end_time)

        val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
        val startTime = cursor.getString(cursor.getColumnIndexOrThrow("start_time"))
        val endTime = cursor.getString(cursor.getColumnIndexOrThrow("end_time"))

        eventName.text = name
        eventStartTime.text = startTime
        eventEndTime.text = endTime
    }
}
