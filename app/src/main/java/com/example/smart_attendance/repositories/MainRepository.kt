package com.example.smart_attendance.repositories

import com.example.smart_attendance.data.Course
import com.example.smart_attendance.data.QRData
import com.example.smart_attendance.data.SessionsData
import com.example.smart_attendance.data.User
import com.example.smart_attendance.other.Resource

interface MainRepository {
    suspend fun getUser(): Resource<User>

    suspend fun logoutUser()

    suspend fun getQRData(qrString: String): Resource<QRData>

    suspend fun attendSession(sessionDetails: QRData): Resource<Boolean>

    suspend fun getCourses(): Resource<List<Course>>

    suspend fun getSessionsDetail(courseId: String): Resource<SessionsData>
}