package com.dev.final_project_diskusinow.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dev.final_project_diskusinow.data.network.ApiConfig
import com.dev.final_project_diskusinow.data.repository.AuthRepository
import com.dev.final_project_diskusinow.data.repository.RoomRepository
import com.dev.final_project_diskusinow.utils.AppExecutors
import com.dev.final_project_diskusinow.utils.UserPreferences

object Injection {
    fun provideAuthRepository(dataStore: DataStore<Preferences>) : AuthRepository {
        val apiService = ApiConfig.getApiServiceNoAuth()
        val appExecutors = AppExecutors()
        val userPreferences = UserPreferences.getInstance(dataStore)
        return AuthRepository.getInstance(apiService, appExecutors, userPreferences)
    }

    fun provideRoomRepository(dataStore: DataStore<Preferences>) : RoomRepository {
        val authRepository = provideAuthRepository(dataStore)
        val apiService = ApiConfig.getApiServiceWithAuth(authRepository)
        val appExecutors = AppExecutors()
        val userPreferences = UserPreferences.getInstance(dataStore)
        return RoomRepository.getInstance(apiService, appExecutors, userPreferences)
    }
}