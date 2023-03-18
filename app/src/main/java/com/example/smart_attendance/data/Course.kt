package com.example.smart_attendance.data

data class Course(
    val _id: String,
    val createdAt: String,
    val instructor: User,
    val name: String,
    val sessions: List<Any>,
    val students: List<User>,
)