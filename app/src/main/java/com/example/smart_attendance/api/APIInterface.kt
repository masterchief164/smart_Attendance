package com.example.smart_attendance.api

import com.example.smart_attendance.data.LoginRequest
import com.example.smart_attendance.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIInterface {
    @POST("/login/googleLogin")
    suspend fun postLogin(@Body loginRequest: LoginRequest): Response<User>

}