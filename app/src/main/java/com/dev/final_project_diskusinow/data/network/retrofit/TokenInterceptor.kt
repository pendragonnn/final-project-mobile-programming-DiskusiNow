package com.dev.final_project_diskusinow.data.network.retrofit

import com.dev.final_project_diskusinow.data.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val authRepository: AuthRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        runBlocking {
            authRepository.getUserToken().collect{ token ->
                token?.let {
                    requestBuilder.addHeader("Authorization", "Bearer $it")
                }
            }
        }
        return chain.proceed(requestBuilder.build())
    }
}