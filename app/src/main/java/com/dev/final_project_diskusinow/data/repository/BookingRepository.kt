package com.dev.final_project_diskusinow.data.repository

import com.dev.final_project_diskusinow.data.network.request.BookingRequest
import com.dev.final_project_diskusinow.data.network.response.BookingResponse
import com.dev.final_project_diskusinow.data.network.retrofit.ApiService
import com.dev.final_project_diskusinow.utils.AppExecutors
import com.dev.final_project_diskusinow.utils.JsonUtils
import com.dev.final_project_diskusinow.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class BookingRepository private constructor(
    private val apiService: ApiService,
    private val appExecutors: AppExecutors,
){

    suspend fun postBookingRoom(bookingRequest: BookingRequest) : Flow<Result<BookingResponse>> = flow {
        emit(Result.Loading)
        try {
            val response = withContext(appExecutors.networkIoDispatcher) {
                apiService.postBooking(bookingRequest)
            }
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorMessage = JsonUtils.extractMessageFromJson(errorBody) ?: "HTTP error occurred"
            emit(Result.Error(errorMessage))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(appExecutors.networkIoDispatcher)
    companion object {
        private var INSTANCE: BookingRepository? = null

        fun getInstance(
            apiService: ApiService,
            appExecutors: AppExecutors
        ): BookingRepository {
            return INSTANCE ?: synchronized(this) {
                BookingRepository(apiService, appExecutors).also {
                    INSTANCE = it
                }
            }
        }
    }
}