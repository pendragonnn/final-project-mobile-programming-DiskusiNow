package com.dev.final_project_diskusinow.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dev.final_project_diskusinow.data.network.ApiConfig
import com.dev.final_project_diskusinow.data.repository.UserRepository
import com.dev.final_project_diskusinow.utils.AppExecutors
import com.dev.final_project_diskusinow.utils.UserPreferences

object Injection {
    fun provideUserRepository(context: Context, dataStore: DataStore<Preferences>) : UserRepository {
        val apiService = ApiConfig.getApiService()
        val appExecutors = AppExecutors()
        val userPreferences = UserPreferences.getInstance(dataStore)
        return UserRepository.getInstance(apiService, appExecutors, userPreferences)
    }
}