package com.example.scheduleapp.usecase

import com.example.scheduleapp.data.repo.FirebaseRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CheckExistUserEventDBUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(userEmail: String) = callbackFlow {
        firebaseRepository.getFirebaseFireStore().collection(userEmail).document("event").get()
            .addOnCompleteListener {
                trySend(it.isSuccessful && it.result.exists())
            }
        awaitClose()
    }
}