package com.example.fundallassessment.di

import com.example.fundallassessment.data.local.sharedPrefs.SharedPrefsManager
import com.example.fundallassessment.data.local.sharedPrefs.SharedPrefsManagerImpl
import com.example.fundallassessment.domain.interactors.SessionManager
import com.example.fundallassessment.domain.repository.Repository
import com.example.fundallassessment.domain.repository.RepositoryImpl
import com.example.fundallassessment.domain.usecases.SessionManagerImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesGson(): Gson = Gson()

    @Singleton
    @Provides
    fun providesSharedPrefManager(sharedPrefsManager: SharedPrefsManagerImpl): SharedPrefsManager =
        sharedPrefsManager

    @Singleton
    @Provides
    fun providesSessionManager(sessionManager: SessionManagerImpl): SessionManager = sessionManager

    @Singleton
    @Provides
    fun providesRepository(repositoryImpl: RepositoryImpl): Repository = repositoryImpl

    @Singleton
    @Provides
    fun providesIODispatcher() : CoroutineContext = Dispatchers.IO
}