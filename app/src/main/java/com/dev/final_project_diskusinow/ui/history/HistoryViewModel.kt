package com.dev.final_project_diskusinow.ui.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.final_project_diskusinow.data.network.response.DataHistory
import com.dev.final_project_diskusinow.data.repository.HistoryRepository
import com.dev.final_project_diskusinow.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class HistoryViewModel(private val historyRepository : HistoryRepository) : ViewModel() {

    private val _dataHistory = MutableSharedFlow<Result<List<DataHistory?>?>>()
    val dataHistory : Flow<Result<List<DataHistory?>?>> = _dataHistory.asSharedFlow()

    init {
        getHistoryUser()
    }
    fun getHistoryUser() {
        Log.d("API HISTORY", "api")
        viewModelScope.launch {
            historyRepository.getHistoryUser().collect{ result -> _dataHistory.emit(result)}
        }
        Log.d("API HISTORY", "clear api")
    }
}