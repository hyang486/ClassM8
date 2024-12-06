package com.example.classm8.di

import com.example.classm8.data.repo.FirebaseRepository
import com.example.classm8.data.repo.FirebaseRepositoryImpl
import com.example.classm8.data.source.FirebaseRemoteDataSource
import com.example.classm8.data.source.FirebaseRemoteDataSourceImpl
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