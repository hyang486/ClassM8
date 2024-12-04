package com.example.scheduleapp.usecase

import com.example.scheduleapp.data.repo.FirebaseRepository
import javax.inject.Inject

class FirebaseLogoutUseCase @Inject constructor(private val firebaseRepository: FirebaseRepository) {
    operator fun invoke() = firebaseRepository.logout()
}