package com.dev.final_project_diskusinow.UI

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.final_project_diskusinow.UI.login.LoginViewModel
import com.dev.final_project_diskusinow.UI.register.RegisterViewModel
import com.dev.final_project_diskusinow.UI.roomInformation.RoomViewModel
import com.dev.final_project_diskusinow.data.repository.AuthRepository
import com.dev.final_project_diskusinow.data.repository.RoomRepository
import com.dev.final_project_diskusinow.di.Injection

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "application")
class ViewModelFactory  private constructor(
    private val authRepository: AuthRepository,
    private val roomRepository: RoomRepository
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
                ViewModelFactory(authRepository, roomRepository).also {
                    INSTANCE = it
                }
            }
        }
    }
}