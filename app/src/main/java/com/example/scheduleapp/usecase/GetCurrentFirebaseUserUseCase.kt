package com.example.scheduleapp.usecase

import com.example.scheduleapp.data.repo.FirebaseRepository
import javax.inject.Inject

class GetCurrentFirebaseUserUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    operator fun invoke() = firebaseRepository.getFirebaseAuth().currentUser
}