package com.example.scheduleapp.usecase

import com.example.scheduleapp.data.model.Event
import com.example.scheduleapp.data.repo.FirebaseRepository
import com.example.scheduleapp.ext.toCallbackFlow
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val currentFirebaseUserUseCase: GetCurrentFirebaseUserUseCase
) {

    operator fun invoke(event: Event) =
        firebaseRepository.deleteEvent(currentFirebaseUserUseCase()?.email.orEmpty(),event).toCallbackFlow()
}