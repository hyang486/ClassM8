package com.cs407.classm8

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val context: Context, private val cursor: Cursor) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    // ViewHolder 정의
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventName: TextView = itemView.findViewById(R.id.event_name)
        val eventStartTime: TextView = itemView.findViewById(R.id.event_start_time)
        val eventEndTime: TextView = itemView.findViewById(R.id.event_end_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.event_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (cursor.moveToPosition(position)) {
            // Cursor에서 데이터 가져오기
            val eventName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val startTime = cursor.getString(cursor.getColumnIndexOrThrow("start_time"))
            val endTime = cursor.getString(cursor.getColumnIndexOrThrow("end_time"))

            // ViewHolder에 데이터 바인딩
            holder.eventName.text = eventName
            holder.eventStartTime.text = startTime
            holder.eventEndTime.text = endTime
        }
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    fun getCursorAtPosition(position: Int): Cursor {
        cursor.moveToPosition(position)
        return cursor
    }
}
