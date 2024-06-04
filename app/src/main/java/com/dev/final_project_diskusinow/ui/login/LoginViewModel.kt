package com.dev.final_project_diskusinow.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.final_project_diskusinow.data.network.request.LoginRequest
import com.dev.final_project_diskusinow.data.network.response.LoginResponse
import com.dev.final_project_diskusinow.data.repository.AuthRepository
import com.dev.final_project_diskusinow.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _loginResult = MutableSharedFlow<Result<LoginResponse>>()
    val loginResult: Flow<Result<LoginResponse>> = _loginResult.asSharedFlow()

    fun loginUser(loginRequest: LoginRequest) {
        viewModelScope.launch {
            authRepository.loginUser(loginRequest)
                .collect { result -> _loginResult.emit(result) }
        }
    }

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            authRepository.saveUserToken(token)
        }
    }
}

