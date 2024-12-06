package com.example.classm8.usecase

import com.example.classm8.data.repo.FirebaseRepository
import com.example.classm8.ext.toCallbackFlow
import javax.inject.Inject

class FirebaseRegisterUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {

    operator fun invoke(email: String, password: String) =
        firebaseRepository.register(email, password).toCallbackFlow()
}