package com.example.classm8.ext


import com.google.android.gms.tasks.Task
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun <T> Task<T>.toCallbackFlow() = callbackFlow {
    addOnSuccessListener {
        trySend(true)
    }.addOnFailureListener {
        trySend(false)
    }
    awaitClose()
}
