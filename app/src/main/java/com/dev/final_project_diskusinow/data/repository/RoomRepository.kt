package com.dev.final_project_diskusinow.data.repository

import com.dev.final_project_diskusinow.data.network.response.DataItem
import com.dev.final_project_diskusinow.data.network.retrofit.ApiService
import com.dev.final_project_diskusinow.utils.AppExecutors
import com.dev.final_project_diskusinow.utils.JsonUtils
import com.dev.final_project_diskusinow.utils.Result
import com.dev.final_project_diskusinow.utils.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RoomRepository private constructor(
    private val apiService: ApiService,
    private val appExecutors: AppExecutors,
    private val userPreferences: UserPreferences
){

    suspend fun getAllRoom() : Flow<Result<List<DataItem?>?>> = flow {
        emit(Result.Loading)
        try {
            val response = withContext(appExecutors.networkIoDispatcher) {
                apiService.getRoom()
            }
            emit(Result.Success(response.data))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = JsonUtils.extractMessageFromJson(errorBody) ?: "HTTP error occurred"
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(appExecutors.networkIoDispatcher)

    companion object {
        private var INSTANCE: RoomRepository? = null

        fun getInstance(
            apiService: ApiService,
            appExecutors: AppExecutors,
            userPreferences: UserPreferences
        ): RoomRepository {
            return INSTANCE ?: synchronized(this) {
                RoomRepository(apiService, appExecutors, userPreferences).also {
                    INSTANCE = it
                }
            }
        }
    }
}
