package com.dev.final_project_diskusinow.data.network.retrofit

import com.dev.final_project_diskusinow.data.network.request.LoginRequest
import com.dev.final_project_diskusinow.data.network.request.RegisterRequest
import com.dev.final_project_diskusinow.data.network.response.LoginResponse
import com.dev.final_project_diskusinow.data.network.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    ) : LoginResponse

    @POST("register")
    suspend  fun registerUser(
        @Body registerRequest: RegisterRequest
    ) : RegisterResponse
}