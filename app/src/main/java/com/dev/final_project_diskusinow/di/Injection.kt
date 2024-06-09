package com.dev.final_project_diskusinow.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dev.final_project_diskusinow.data.network.ApiConfig
import com.dev.final_project_diskusinow.data.repository.AuthRepository
import com.dev.final_project_diskusinow.data.repository.BookingRepository
import com.dev.final_project_diskusinow.data.repository.HistoryRepository
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
        return RoomRepository.getInstance(apiService, appExecutors)
    }

    fun provideHistoryRepository(dataStore: DataStore<Preferences>) : HistoryRepository {
        val authRepository = provideAuthRepository(dataStore)
        val apiService = ApiConfig.getApiServiceWithAuth(authRepository)
        val appExecutors = AppExecutors()
        return HistoryRepository.getInstance(apiService, appExecutors)
    }
    fun provideBookingRepository(dataStore: DataStore<Preferences>): BookingRepository {
        val authRepository = provideAuthRepository(dataStore)
        val apiService = ApiConfig.getApiServiceWithAuth(authRepository)
        val appExecutors = AppExecutors()
        return BookingRepository.getInstance(apiService, appExecutors)
    }
}