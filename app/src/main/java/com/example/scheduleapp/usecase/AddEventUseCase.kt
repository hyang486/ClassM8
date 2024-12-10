package com.example.scheduleapp.usecase

import com.example.scheduleapp.data.model.Event
import com.example.scheduleapp.data.repo.FirebaseRepository
import com.example.scheduleapp.ext.toCallbackFlow
import javax.inject.Inject

class AddEventUseCase @Inject constructor(
    private val currentFirebaseUserUseCase: GetCurrentFirebaseUserUseCase,
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(event: Event) =
        firebaseRepository.addEvent(currentFirebaseUserUseCase()?.email.orEmpty(), event)
            .toCallbackFlow()
}