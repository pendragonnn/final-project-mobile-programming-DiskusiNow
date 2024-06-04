package com.dev.final_project_diskusinow.data.network.response

data class RoomResponse(
	val data: List<DataItem?>? = null,
	val message: String? = null,
	val status: Boolean? = null
)

data class SlotsItem(
	val room_id: Int? = null,
	val start_time: String? = null,
	val createdAt: String? = null,
	val end_time: String? = null,
	val id: Int? = null,
	val is_booked: Boolean? = null,
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

