package com.shaswat.smart_attendance.repositories

import com.shaswat.smart_attendance.data.Course
import com.shaswat.smart_attendance.data.QRData
import com.shaswat.smart_attendance.data.SessionsData
import com.shaswat.smart_attendance.data.User
import com.shaswat.smart_attendance.other.Resource
import java.io.File

interface MainRepository {
    suspend fun getUser(): Resource<User>

    suspend fun logoutUser()

    suspend fun getQRData(qrString: String): Resource<QRData>

    suspend fun attendSession(sessionDetails: QRData): Resource<Boolean>

    suspend fun getCourses(): Resource<List<Course>>

    suspend fun getSessionsDetail(courseId: String): Resource<SessionsData>

    suspend fun uploadImages(files: ArrayList<File>): Resource<Boolean>
}