package com.example.smart_attendance.data

data class SessionsData(
    val attendanceCount: Int,
    val sessionDates: List<String>,
    val sessionIds: List<String>,
    val sessions: List<Session>,
    val sessionsCount: Int
)