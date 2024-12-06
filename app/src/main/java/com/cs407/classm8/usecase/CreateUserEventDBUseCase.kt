package com.example.classm8.usecase

import com.example.classm8.data.repo.FirebaseRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CreateUserEventDBUseCase @Inject constructor(
    private val checkExistUserEventDBUseCase: CheckExistUserEventDBUseCase,
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke(userEmail: String) = callbackFlow {
        if (checkExistUserEventDBUseCase(userEmail).first()) {
            trySend(true)
        } else {
            firebaseRepository.createEventDB(userEmail).addOnSuccessListener {
                trySend(true)
            }.addOnFailureListener {
                trySend(false)
            }
        }
        awaitClose()
    }
}