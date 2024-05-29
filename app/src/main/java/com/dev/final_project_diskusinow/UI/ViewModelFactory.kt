package com.dev.final_project_diskusinow.UI

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.final_project_diskusinow.data.repository.UserRepository
import com.dev.final_project_diskusinow.di.Injection

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "application")
class ViewModelFactory  private constructor(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                ViewModelFactory(Injection.provideUserRepository(context, context.dataStore)).also {
                    INSTANCE = it
                }
            }
        }
    }
}