package com.cs407.classm8

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class AddEventActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var detailsEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var startTimeEditText: EditText
    private lateinit var endTimeEditText: EditText
    private lateinit var finishEditText: EditText
    private lateinit var repeatToggle: ToggleButton
    private lateinit var addButton: Button
    private lateinit var cancelButton: Button
    private lateinit var dbHelper: UserDatabaseHelper

    private var eventId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        dbHelper = UserDatabaseHelper(this)

        nameEditText = findViewById(R.id.name)
        detailsEditText = findViewById(R.id.details)
        dateEditText = findViewById(R.id.date)
        startTimeEditText = findViewById(R.id.start_time)
        endTimeEditText = findViewById(R.id.end_time)
        finishEditText = findViewById(R.id.finish)
        repeatToggle = findViewById(R.id.repeat_toggle)
        addButton = findViewById(R.id.add_button)
        cancelButton = findViewById(R.id.cancel_button)

        eventId = intent.getIntExtra("eventId", -1)
        if (eventId != -1) {
            loadEventDetails(eventId)
            addButton.text = "Update"
        }

        dateEditText.setOnClickListener { showDatePicker(dateEditText) }
        finishEditText.setOnClickListener { showDatePicker(finishEditText) }
        startTimeEditText.setOnClickListener { showTimePicker(startTimeEditText) }
        endTimeEditText.setOnClickListener { showTimePicker(endTimeEditText) }

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val details = detailsEditText.text.toString()
            val date = dateEditText.text.toString()
            val startTime = startTimeEditText.text.toString()
            val endTime = endTimeEditText.text.toString()
            val finish = finishEditText.text.toString()
            val repeat = repeatToggle.isChecked

            if (name.isNotEmpty() && date.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty()) {
                if (eventId == -1) {
                    dbHelper.addEvent(name, details, date, startTime, endTime, repeat, finish)
                    Toast.makeText(this, "Event Added", Toast.LENGTH_SHORT).show()
                } else {
                    dbHelper.updateEvent(eventId, name, details, date, startTime, endTime)
                    Toast.makeText(this, "Event Updated", Toast.LENGTH_SHORT).show()
                }
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            }
        }

        cancelButton.setOnClickListener { finish() }
    }

    private fun showDatePicker(targetEditText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            targetEditText.setText("$year-${month + 1}-$dayOfMonth")
        }, year, month, day).show()
    }

    private fun showTimePicker(targetEditText: EditText) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, hourOfDay, minute ->
            targetEditText.setText(String.format("%02d:%02d", hourOfDay, minute))
        }, hour, minute, true).show()
    }

    private fun loadEventDetails(eventId: Int) {
        val cursor = dbHelper.getEventById(eventId)
        if (cursor.moveToFirst()) {
            nameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")))
            detailsEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("details")))
            dateEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("date")))
            startTimeEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("start_time")))
            endTimeEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("end_time")))
            finishEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("finish")))
            repeatToggle.isChecked = cursor.getInt(cursor.getColumnIndexOrThrow("repeat")) == 1
        }
        cursor.close()
    }
}
