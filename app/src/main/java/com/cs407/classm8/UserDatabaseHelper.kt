package com.cs407.classm8

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USERNAME TEXT PRIMARY KEY, 
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()
        db.execSQL(createUsersTable)

        val createEventsTable = """
            CREATE TABLE $TABLE_EVENTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
                $COLUMN_NAME TEXT, 
                $COLUMN_DETAILS TEXT, 
                $COLUMN_DATE TEXT, 
                $COLUMN_START_TIME TEXT, 
                $COLUMN_END_TIME TEXT, 
                $COLUMN_REPEAT INTEGER, 
                $COLUMN_FINISH TEXT
            )
        """.trimIndent()
        db.execSQL(createEventsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EVENTS")
        onCreate(db)
    }

    // Add a user
    fun addUser(username: String, password: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    // Check if user exists
    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_USERNAME),
            "$COLUMN_USERNAME=? AND $COLUMN_PASSWORD=?",
            arrayOf(username, password),
            null, null, null
        )
        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0
    }

    // Check if username exists
    fun checkUserExists(username: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_USERNAME),
            "$COLUMN_USERNAME=?",
            arrayOf(username),
            null, null, null
        )
        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0
    }

    // Add a new event
    fun addEvent(name: String, details: String, date: String, startTime: String, endTime: String, repeat: Boolean, finish: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DETAILS, details)
            put(COLUMN_DATE, date)
            put(COLUMN_START_TIME, startTime)
            put(COLUMN_END_TIME, endTime)
            put(COLUMN_REPEAT, if (repeat) 1 else 0)
            put(COLUMN_FINISH, finish)
        }
        db.insert(TABLE_EVENTS, null, values)
        db.close()
    }

    // Get events for a specific day
    fun getEventsForDay(day: String): Cursor {
        val db = this.readableDatabase
        return db.query(
            TABLE_EVENTS,
            arrayOf( // 반환할 컬럼 목록
                "$COLUMN_ID AS _id", // 반드시 `_id` 열 포함
                COLUMN_NAME,
                COLUMN_START_TIME,
                COLUMN_END_TIME
            ),
            "$COLUMN_DATE=? OR ($COLUMN_REPEAT=1 AND $COLUMN_FINISH>=?)", // 조건
            arrayOf(day, day), // 조건의 값
            null, // 그룹화
            null, // 필터 조건
            "$COLUMN_START_TIME ASC" // 정렬 순서
        )
    }


    // Get a specific event by ID
    fun getEventById(eventId: Int): Cursor {
        val db = this.readableDatabase
        return db.query(
            TABLE_EVENTS,
            arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_DETAILS, COLUMN_DATE, COLUMN_START_TIME, COLUMN_END_TIME, COLUMN_REPEAT, COLUMN_FINISH),
            "$COLUMN_ID=?",
            arrayOf(eventId.toString()),
            null, null, null
        )
    }

    // Delete an event
    fun deleteEvent(eventId: Int) {
        val db = writableDatabase
        db.delete(TABLE_EVENTS, "$COLUMN_ID=?", arrayOf(eventId.toString()))
        db.close()
    }

    // Update an event
    fun updateEvent(eventId: Int, name: String, details: String, date: String, startTime: String, endTime: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_DETAILS, details)
            put(COLUMN_DATE, date)
            put(COLUMN_START_TIME, startTime)
            put(COLUMN_END_TIME, endTime)
        }
        db.update(TABLE_EVENTS, values, "$COLUMN_ID=?", arrayOf(eventId.toString()))
        db.close()
    }

    companion object {
        private const val DATABASE_NAME = "userDatabase.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_USERS = "users"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"

        private const val TABLE_EVENTS = "events"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_DETAILS = "details"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_START_TIME = "start_time"
        private const val COLUMN_END_TIME = "end_time"
        private const val COLUMN_REPEAT = "repeat"
        private const val COLUMN_FINISH = "finish"
    }
}
