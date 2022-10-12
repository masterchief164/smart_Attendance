package com.example.smart_attendance.api

import com.example.smart_attendance.data.LoginRequest
import com.example.smart_attendance.data.QRData
import com.example.smart_attendance.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface {
    @POST("/login/googleLogin")
    suspend fun postLogin(@Body loginRequest: LoginRequest): Response<User>

    @POST("/session/attend")
    suspend fun postAttendance(
        @Header("Cookie") token: String,
        @Body sessionDetails: QRData
    ): Response<Boolean>

}