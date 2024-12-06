package com.example.classm8.usecase

import com.example.classm8.data.repo.FirebaseRepository
import javax.inject.Inject

class FirebaseLogoutUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    operator fun invoke() = firebaseRepository.logout()
}