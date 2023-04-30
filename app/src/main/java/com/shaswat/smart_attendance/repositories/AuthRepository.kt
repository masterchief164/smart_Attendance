package com.shaswat.smart_attendance.repositories

import com.shaswat.smart_attendance.data.User
import com.shaswat.smart_attendance.other.Resource

interface AuthRepository {
    suspend fun loginUser(idToken: String): Resource<User>

    fun saveCookie(cookie: String)

    fun saveUser(user: User)
}