package com.example.scheduleapp.di

import com.example.scheduleapp.data.repo.FirebaseRepository
import com.example.scheduleapp.data.repo.FirebaseRepositoryImpl
import com.example.scheduleapp.data.source.FirebaseRemoteDataSource
import com.example.scheduleapp.data.source.FirebaseRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseRemoteDataSource(firebaseRemoteDataSourceImpl: FirebaseRemoteDataSourceImpl): FirebaseRemoteDataSource

}