package com.example.scheduleapp.usecase

import com.example.scheduleapp.data.model.Event
import com.example.scheduleapp.data.repo.FirebaseRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GetUserEventUseCase @Inject constructor(
    private val currentFirebaseUserUseCase: GetCurrentFirebaseUserUseCase,
    private val firebaseRepository: FirebaseRepository
) {
    operator fun invoke() = callbackFlow<List<Event>> {
        firebaseRepository.getFirebaseFireStore().collection(
            currentFirebaseUserUseCase()?.email.orEmpty()
        ).document("event").addSnapshotListener { value, error ->
            if(error != null) trySend(emptyList())
            else if(value?.exists() == true) {
                if(value["list"] == null) trySend(emptyList())
                else trySend(Gson().fromJson(value["list"]))
            }
        }
        awaitClose()
    }

    private inline fun <reified T> Gson.fromJson(json: Any?): T =
        fromJson(toJson(json), object : TypeToken<T>() {}.type)
}