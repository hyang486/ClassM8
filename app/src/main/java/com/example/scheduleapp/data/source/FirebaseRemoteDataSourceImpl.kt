package com.example.scheduleapp.data.source

import com.example.scheduleapp.data.model.Event
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : FirebaseRemoteDataSource {

    override fun login(id: String, password: String): Task<AuthResult> =
        firebaseAuth.signInWithEmailAndPassword(id, password)

    override fun logout(): Boolean {
        firebaseAuth.signOut()
        return firebaseAuth.currentUser == null
    }

    override fun register(id: String, password: String): Task<AuthResult> =
        firebaseAuth.createUserWithEmailAndPassword(id, password)

    override fun createEventDB(id: String): Task<Void> =
        fireStore.collection(id).document("event").set(emptyMap<String, Event>())

    override fun addEvent(id: String, event: Event): Task<Void> =
        fireStore.collection(id).document("event").update("list", FieldValue.arrayUnion(event))

    override fun deleteEvent(id: String, event: Event): Task<Void> =
        fireStore.collection(id).document("event").update("list", FieldValue.arrayRemove(event))

    override fun getFirebaseAuth(): FirebaseAuth =
        firebaseAuth

    override fun getFirebaseFireStore(): FirebaseFirestore =
        fireStore
}