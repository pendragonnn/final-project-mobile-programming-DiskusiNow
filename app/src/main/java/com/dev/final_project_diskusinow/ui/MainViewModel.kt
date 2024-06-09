package com.dev.final_project_diskusinow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.final_project_diskusinow.data.network.request.BookingRequest
import com.dev.final_project_diskusinow.data.network.response.BookingResponse
import com.dev.final_project_diskusinow.data.repository.AuthRepository
import com.dev.final_project_diskusinow.data.repository.BookingRepository
import com.dev.final_project_diskusinow.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(private val authRepository: AuthRepository, private val bookingRepository: BookingRepository) : ViewModel(){
    private val _bookingResult = MutableSharedFlow<Result<BookingResponse>>()
    val bookingResult: Flow<Result<BookingResponse>> = _bookingResult.asSharedFlow()

    private val _isLogout = MutableSharedFlow<Boolean>()
    val isLogout: Flow<Boolean> = _isLogout.asSharedFlow()

    fun postBookingRoom(bookingRequest: BookingRequest) {
        viewModelScope.launch {
            bookingRepository.postBookingRoom(bookingRequest)
                .collect{ result -> _bookingResult.emit(result)}
        }
    }
    fun logout() {
        viewModelScope.launch {
            authRepository.deleteUserToken()
            _isLogout.emit(true)
        }
    }
}