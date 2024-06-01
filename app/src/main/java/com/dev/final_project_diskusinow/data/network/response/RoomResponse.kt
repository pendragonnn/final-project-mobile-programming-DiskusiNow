package com.dev.final_project_diskusinow.data.network.response

data class RoomResponse(
	val data: List<DataItem?>? = null,
	val message: String? = null,
	val status: Boolean? = null
)

data class SlotsItem(
	val roomId: Int? = null,
	val startTime: String? = null,
	val createdAt: String? = null,
	val endTime: String? = null,
	val id: Int? = null,
	val isBooked: Boolean? = null,
	val updatedAt: String? = null
)

data class DataItem(
	val createdAt: String? = null,
	val slots: List<SlotsItem?>? = null,
	val name: String? = null,
	val id: Int? = null,
	val floor: Int? = null,
	val capacity: Int? = null,
	val updatedAt: String? = null
)

