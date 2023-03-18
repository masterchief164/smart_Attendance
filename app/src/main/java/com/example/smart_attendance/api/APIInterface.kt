package com.example.smart_attendance.api

import com.example.smart_attendance.data.Course
import com.example.smart_attendance.data.LoginRequest
import com.example.smart_attendance.data.QRData
import com.example.smart_attendance.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface {
    @POST("/auth/googleLogin")
    suspend fun postLogin(@Body loginRequest: LoginRequest): Response<User>

    @POST("/session/attend")
    suspend fun postAttendance(
        @Header("Cookie") token: String,
        @Body sessionDetails: QRData
    ): Response<Boolean>

    @GET("/course")
    suspend fun getCourses(
        @Header("Cookie") token: String
    ): Response<List<Course>>

}