package com.example.classm8.usecase

import com.example.classm8.data.repo.FirebaseRepository
import javax.inject.Inject

class GetCurrentFirebaseUserUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    operator fun invoke() = firebaseRepository.getFirebaseAuth().currentUser
}