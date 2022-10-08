package com.example.smart_attendance.repositories

import com.example.smart_attendance.data.QRData
import com.example.smart_attendance.data.User
import com.example.smart_attendance.other.Resource

interface MainRepository {
    suspend fun getUser(): Resource<User>

    suspend fun logoutUser()

    suspend fun getQRData(qrString: String): Resource<QRData>
}