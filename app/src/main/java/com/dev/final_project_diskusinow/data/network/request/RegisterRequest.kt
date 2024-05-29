package com.dev.final_project_diskusinow.data.network.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val nim: String,
    val password: String
)
