package com.example.scheduleapp.usecase

import com.example.scheduleapp.data.repo.FirebaseRepository
import com.example.scheduleapp.ext.toCallbackFlow
import javax.inject.Inject

class FirebaseLoginUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    operator fun invoke(email: String, password: String) =
        firebaseRepository.login(email, password).toCallbackFlow()
}