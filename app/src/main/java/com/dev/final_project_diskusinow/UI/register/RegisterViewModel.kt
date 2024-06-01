package com.dev.final_project_diskusinow.UI.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.final_project_diskusinow.data.network.request.RegisterRequest
import com.dev.final_project_diskusinow.data.network.response.RegisterResponse
import com.dev.final_project_diskusinow.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import com.dev.final_project_diskusinow.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: AuthRepository) : ViewModel()  {

    private val _registerResult = MutableSharedFlow<Result<RegisterResponse>>()
    val registerResult: Flow<Result<RegisterResponse>> = _registerResult.asSharedFlow()

    fun registerUser(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            userRepository.registerUser(registerRequest)
                .collect{ result -> _registerResult.emit(result)}
        }
    }


}