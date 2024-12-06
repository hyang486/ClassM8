package com.example.classm8.data.repo

import com.example.classm8.data.model.Event
import com.example.classm8.data.source.FirebaseRemoteDataSource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseRemoteDataSource: FirebaseRemoteDataSource
) : FirebaseRepository {

    override fun login(id: String, password: String): Task<AuthResult> =
        firebaseRemoteDataSource.login(id, password)

    override fun logout(): Boolean =
        firebaseRemoteDataSource.logout()

    override fun register(id: String, password: String): Task<AuthResult> =
        firebaseRemoteDataSource.register(id, password)

    override fun createEventDB(id: String): Task<Void> =
        firebaseRemoteDataSource.createEventDB(id)

    override fun addEvent(id: String, event: Event): Task<Void> =
        firebaseRemoteDataSource.addEvent(id, event)

    override fun deleteEvent(id: String, event: Event): Task<Void> =
        firebaseRemoteDataSource.deleteEvent(id, event)

    override fun getFirebaseAuth(): FirebaseAuth =
        firebaseRemoteDataSource.getFirebaseAuth()

    override fun getFirebaseFireStore(): FirebaseFirestore =
        firebaseRemoteDataSource.getFirebaseFireStore()
}