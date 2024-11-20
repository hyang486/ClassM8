package com.cs407.classm8

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CurrentScheduleActivity : AppCompatActivity() {

    private lateinit var dbHelper: UserDatabaseHelper
    private lateinit var eventListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_schedule)

        dbHelper = UserDatabaseHelper(this)
        eventListView = findViewById(R.id.event_list)

        refreshData()
    }

    private fun refreshData() {
        val dayOfWeek = getCurrentDayOfWeek()
        val cursor: Cursor = dbHelper.getEventsForDay(dayOfWeek)

        if (cursor.count == 0) {
            findViewById<TextView>(R.id.no_event_text).visibility = View.VISIBLE
        } else {
            findViewById<TextView>(R.id.no_event_text).visibility = View.GONE
        }

        val adapter = CustomCursorAdapter(this, cursor)
        eventListView.adapter = adapter
    }

    private fun getCurrentDayOfWeek(): String {
        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH) + 1
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        return "%04d-%02d-%02d".format(year, month, day)
    }
}
