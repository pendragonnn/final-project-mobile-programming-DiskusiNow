package com.dev.final_project_diskusinow.data.network.retrofit

import com.dev.final_project_diskusinow.data.network.request.BookingRequest
import com.dev.final_project_diskusinow.data.network.request.LoginRequest
import com.dev.final_project_diskusinow.data.network.request.RegisterRequest
import com.dev.final_project_diskusinow.data.network.response.BookingResponse
import com.dev.final_project_diskusinow.data.network.response.HistoryUserResponse
import com.dev.final_project_diskusinow.data.network.response.LoginResponse
import com.dev.final_project_diskusinow.data.network.response.RegisterResponse
import com.dev.final_project_diskusinow.data.network.response.RoomResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @NoAuth
    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ) : LoginResponse


    @POST("register")
    suspend  fun registerUser(
        @Body registerRequest: RegisterRequest
    ) : RegisterResponse

    @GET("room")
    suspend fun getRoom() : RoomResponse

    @GET("user/history")
    suspend fun getHistory() : HistoryUserResponse

    @POST("booking")
    suspend fun postBooking(
        @Body bookingRequest: BookingRequest
    ) : BookingResponse
}

annotation class NoAuth
