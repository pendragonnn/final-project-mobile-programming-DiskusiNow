package com.dev.final_project_diskusinow.data.repository

import com.dev.final_project_diskusinow.utils.AppExecutors
import com.dev.final_project_diskusinow.utils.UserPreferences

class UserRepository private constructor(
    private val appExecutors: AppExecutors,
    private val userPreferences: UserPreferences
){

    companion object {
        private var INSTANCE: UserRepository? = null

        fun getInstance(
            appExecutors: AppExecutors,
            userPreferences: UserPreferences
        ): UserRepository {
            return INSTANCE ?: synchronized(this) {
                UserRepository(appExecutors, userPreferences).also {
                    INSTANCE = it
                }
            }
        }
    }
}