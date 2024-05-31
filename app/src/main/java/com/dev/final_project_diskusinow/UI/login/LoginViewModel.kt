package com.dev.final_project_diskusinow.UI.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.final_project_diskusinow.data.network.request.LoginRequest
import com.dev.final_project_diskusinow.data.network.response.LoginResponse
import com.dev.final_project_diskusinow.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import com.dev.final_project_diskusinow.utils.Result
import kotlinx.coroutines.flow.asSharedFlow

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _loginResult = MutableSharedFlow<Result<LoginResponse>>()
    val loginResult: Flow<Result<LoginResponse>> = _loginResult.asSharedFlow()

    fun loginUser(loginRequest: LoginRequest) {
        viewModelScope.launch {
            userRepository.loginUser(loginRequest)
                .collect { result -> _loginResult.emit(result) }
        }
    }

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
        }
    }
}

