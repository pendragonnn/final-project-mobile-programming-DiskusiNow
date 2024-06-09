package com.dev.final_project_diskusinow.data.repository

import com.dev.final_project_diskusinow.data.network.request.LoginRequest
import com.dev.final_project_diskusinow.data.network.request.RegisterRequest
import com.dev.final_project_diskusinow.data.network.response.LoginResponse
import com.dev.final_project_diskusinow.data.network.response.RegisterResponse
import com.dev.final_project_diskusinow.data.network.retrofit.ApiService
import com.dev.final_project_diskusinow.utils.AppExecutors
import com.dev.final_project_diskusinow.utils.JsonUtils.extractMessageFromJson
import com.dev.final_project_diskusinow.utils.Result
import com.dev.final_project_diskusinow.utils.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AuthRepository private constructor(
    private val apiService: ApiService,
    private val appExecutors: AppExecutors,
    private val userPreferences: UserPreferences
){

    suspend fun loginUser(loginRequest: LoginRequest) : Flow<Result<LoginResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = withContext(appExecutors.networkIoDispatcher) {
                apiService.loginUser(loginRequest)
            }
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = extractMessageFromJson(errorBody) ?: "HTTP error occurred"
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(appExecutors.networkIoDispatcher)


    suspend fun registerUser(registerRequest: RegisterRequest) : Flow<Result<RegisterResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = withContext(appExecutors.networkIoDispatcher) {
                apiService.registerUser(registerRequest)
            }
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = extractMessageFromJson(errorBody) ?: "HTTP error occurred"
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(appExecutors.networkIoDispatcher)
    suspend fun saveUserToken(userToken: String) = userPreferences.saveTokenUser(userToken)

    fun getUserToken() : Flow<String?> = userPreferences.getTokenUser()

    suspend fun deleteUserToken() = userPreferences.deleteTokenUser()
    companion object {
        private var INSTANCE: AuthRepository? = null

        fun getInstance(
            apiService: ApiService,
            appExecutors: AppExecutors,
            userPreferences: UserPreferences
        ): AuthRepository {
            return INSTANCE ?: synchronized(this) {
                AuthRepository(apiService, appExecutors, userPreferences).also {
                    INSTANCE = it
                }
            }
        }
    }
}