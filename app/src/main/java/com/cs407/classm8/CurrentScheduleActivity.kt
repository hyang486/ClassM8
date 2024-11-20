package com.cs407.classm8

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class CurrentScheduleActivity : AppCompatActivity() {

    private lateinit var dbHelper: UserDatabaseHelper
    private lateinit var eventListView: ListView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_schedule)

        // Toolbar 설정
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // DrawerLayout 및 ActionBarDrawerToggle 설정
        drawerLayout = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // NavigationView 설정
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_add_event -> {
                    // AddEventActivity 실행
                    startActivityForResult(
                        Intent(this@CurrentScheduleActivity, AddEventActivity::class.java),
                        ADD_EVENT_REQUEST_CODE
                    )
                }
                R.id.nav_logout -> {
                    finish()
                }
            }
            drawerLayout.closeDrawers()
            true
        }

        // 이벤트 리스트 데이터베이스 연결
        dbHelper = UserDatabaseHelper(this)
        eventListView = findViewById(R.id.event_list)

        refreshData()

        // 리스트 항목 클릭 이벤트
        eventListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val cursor = (eventListView.adapter as CustomCursorAdapter).cursor
            cursor.moveToPosition(position)
            val details = cursor.getString(cursor.getColumnIndexOrThrow("details"))
            Toast.makeText(this, details, Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_EVENT_REQUEST_CODE && resultCode == RESULT_OK) {
            refreshData() // AddEventActivity에서 돌아온 후 데이터 새로고침
        }
    }

    private fun refreshData() {
        val dayOfWeek = getCurrentDayOfWeek()
        val newCursor: Cursor = dbHelper.getEventsForDay(dayOfWeek)

        // 이벤트가 없을 경우 "No events for today" 텍스트 표시
        if (newCursor.count == 0) {
            val noEventTextView: TextView = findViewById(R.id.no_event_text)
            noEventTextView.visibility = View.VISIBLE
        } else {
            val noEventTextView: TextView = findViewById(R.id.no_event_text)
            noEventTextView.visibility = View.GONE
        }

        // CustomCursorAdapter를 사용하여 데이터와 ListView 연결
        val adapter = CustomCursorAdapter(this, newCursor)
        eventListView.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCurrentDayOfWeek(): String {
        val calendar = java.util.Calendar.getInstance()
        return when (calendar.get(java.util.Calendar.DAY_OF_WEEK)) {
            java.util.Calendar.SUNDAY -> "Sunday"
            java.util.Calendar.MONDAY -> "Monday"
            java.util.Calendar.TUESDAY -> "Tuesday"
            java.util.Calendar.WEDNESDAY -> "Wednesday"
            java.util.Calendar.THURSDAY -> "Thursday"
            java.util.Calendar.FRIDAY -> "Friday"
            java.util.Calendar.SATURDAY -> "Saturday"
            else -> ""
        }
    }

    companion object {
        private const val ADD_EVENT_REQUEST_CODE = 1
    }
}
