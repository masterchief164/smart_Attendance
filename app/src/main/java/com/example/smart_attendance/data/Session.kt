package com.example.smart_attendance.data

data class Session(
    val courseId: String,
    val instructor: User,
    val date: String,
    val attendees: User,
)