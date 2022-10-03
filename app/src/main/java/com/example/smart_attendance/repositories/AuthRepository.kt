package com.example.smart_attendance.repositories

import com.example.smart_attendance.data.User
import com.example.smart_attendance.other.Resource

interface AuthRepository {
    suspend fun loginUser(idToken: String): Resource<User>

    fun saveCookie(cookie: String)

    suspend fun saveUser(user: User)
}