package com.dev.final_project_diskusinow.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.final_project_diskusinow.data.repository.AuthRepository
import com.dev.final_project_diskusinow.data.repository.HistoryRepository
import com.dev.final_project_diskusinow.data.repository.RoomRepository
import com.dev.final_project_diskusinow.di.Injection
import com.dev.final_project_diskusinow.ui.history.HistoryViewModel
import com.dev.final_project_diskusinow.ui.login.LoginViewModel
import com.dev.final_project_diskusinow.ui.register.RegisterViewModel
import com.dev.final_project_diskusinow.ui.roomInformation.RoomViewModel

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "application")
class ViewModelFactory  private constructor(
    private val authRepository: AuthRepository,
    private val roomRepository: RoomRepository,
    private val historyRepository: HistoryRepository
) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(RoomViewModel::class.java) -> {
                RoomViewModel(roomRepository) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(historyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                val authRepository = Injection.provideAuthRepository(context.dataStore)
                val roomRepository = Injection.provideRoomRepository(context.dataStore)
                val historyRepository = Injection.provideHistoryRepository(context.dataStore)
                ViewModelFactory(authRepository, roomRepository, historyRepository).also {
                    INSTANCE = it
                }
            }
        }
    }
}