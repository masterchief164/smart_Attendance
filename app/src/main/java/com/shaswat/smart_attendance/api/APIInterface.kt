package com.shaswat.smart_attendance.api

import com.shaswat.smart_attendance.data.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("/session/all/{courseId}")
    suspend fun getSessions(
        @Header("Cookie") token: String,
        @Path("courseId") courseId: String
    ): Response<SessionsData>

    @PATCH("/user")
    suspend fun updateProfile(
        @Header("Cookie") token: String,
        @Body user: UpdateProfile
    ): Response<User>

}