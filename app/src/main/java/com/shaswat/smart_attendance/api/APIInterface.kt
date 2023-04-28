package com.shaswat.smart_attendance.api

import com.shaswat.smart_attendance.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface APIInterface {
    @POST("/auth/googleLogin")
    suspend fun postLogin(@Body loginRequest: LoginRequest): Response<User>

    @POST("/session/attend")
    suspend fun postAttendance(
        @Header("Cookie") token: String,
        @Body sessionDetails: QRData
    ): Response<TempSession>

    @GET("/course")
    suspend fun getCourses(
        @Header("Cookie") token: String
    ): Response<List<Course>>

    @GET("/session/all/{courseId}")
    suspend fun getSessions(
        @Header("Cookie") token: String,
        @Path("courseId") courseId: String
    ): Response<SessionsData>

    @Multipart
    @POST("/session/addFace")
    suspend fun uploadImage(
        @Header("Cookie") token: String,
        @Part image1: MultipartBody.Part,
        @Part image2: MultipartBody.Part,
        @Part image3: MultipartBody.Part,
    )

}