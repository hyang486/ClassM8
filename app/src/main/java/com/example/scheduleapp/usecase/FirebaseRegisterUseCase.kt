package com.example.scheduleapp.usecase

import com.example.scheduleapp.data.repo.FirebaseRepository
import com.example.scheduleapp.ext.toCallbackFlow
import javax.inject.Inject

class FirebaseRegisterUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {

    operator fun invoke(email: String, password: String) =
        firebaseRepository.register(email, password).toCallbackFlow()
}