package com.dev.final_project_diskusinow.data.network.request

data class BookingRequest(
    val room_id: Int,
    val no_phone: String,
    val date: String,
    val participant: Int,
    val time_booking: String,
    val description: String
)