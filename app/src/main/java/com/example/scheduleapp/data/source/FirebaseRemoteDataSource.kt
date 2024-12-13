package com.example.scheduleapp.data.source

import com.example.scheduleapp.data.model.Event
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

interface FirebaseRemoteDataSource {

    fun login(
        id: String,
        password: String
    ): Task<AuthResult>

    fun logout(): Boolean

    fun register(
        id: String,
        password: String
    ): Task<AuthResult>

    fun createEventDB(id : String) : Task<Void>

    fun addEvent(id : String, event : Event) : Task<Void>

    fun deleteEvent(id : String, event : Event) : Task<Void>


    fun getFirebaseAuth(): FirebaseAuth

    fun getFirebaseFireStore(): FirebaseFirestore


}