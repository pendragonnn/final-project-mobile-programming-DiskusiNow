package com.dev.final_project_diskusinow.UI.roomInformation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.final_project_diskusinow.data.network.response.DataItem
import com.dev.final_project_diskusinow.data.repository.RoomRepository
import com.dev.final_project_diskusinow.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RoomViewModel(private val roomRepository: RoomRepository) : ViewModel() {
    private val _dataRoom = MutableSharedFlow<Result<List<DataItem?>?>>()
    val dataRoom : Flow<Result<List<DataItem?>?>> = _dataRoom.asSharedFlow()


    fun getAllRoom() {
        viewModelScope.launch {
            roomRepository.getAllRoom().collect{ result -> _dataRoom.emit(result) }
        }
    }
}