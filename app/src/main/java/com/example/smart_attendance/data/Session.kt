package com.example.smart_attendance.data

data class Session(
    val __v: Int,
    val _id: String,
    val attended: Boolean,
    val attendees: List<String>,
    val courseId: String,
    val createdAt: String,
    val date: String,
    val instructor: String,
    val updatedAt: String
)