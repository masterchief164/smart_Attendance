package com.shaswat.smart_attendance.repositories

import android.content.SharedPreferences
import android.util.Log.d
import android.util.Log.e
import com.shaswat.smart_attendance.api.APIInterface
import com.shaswat.smart_attendance.other.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Tasks.await
import com.google.gson.Gson
import com.shaswat.smart_attendance.data.*
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val retrofitApi: APIInterface,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
    private val googleSignInClient: GoogleSignInClient
) : MainRepository {
    override suspend fun getUser(): Resource<User> {
        val userString = sharedPreferences.getString("USER", null)
        return if (userString != null) {
            val user = gson.fromJson(userString, User::class.java)
            Resource.Success(user)
        } else Resource.Error("User not found")
    }

    override suspend fun logoutUser() {
        sharedPreferences.edit().remove("USER").apply()
        sharedPreferences.edit().remove("COOKIE").apply()
        try {
            await(googleSignInClient.signOut())
        } catch (err: Exception) {
            e("Logout1", err.message.toString())
        }
    }

    override suspend fun getQRData(qrString: String): Resource<QRData> {
        return try {
            val qrData = gson.fromJson(qrString, QRData::class.java)
            Resource.Success(qrData)
        } catch (err: Exception) {
            Resource.Error("Invalid QR Code")
        }
    }

    override suspend fun attendSession(sessionDetails: QRData): Resource<Boolean> {
        val response = try {
            d("Main Activity", "attending session")
            var cookie = sharedPreferences.getString("COOKIE", "")
            cookie = "token=$cookie;"
            d("cookie", cookie.toString())
            retrofitApi.postAttendance(cookie, sessionDetails)
        } catch (e: IOException) {
            e("Main Activity", "IOEException, no internet?", e)
            return Resource.Error("Network Failure")
        } catch (e: HttpException) {
            e("Main Activity", "HTTP exception, unexpected response", e)
            return Resource.Error("Network Failure")
        }
        d("Main Activity", "Response: $response")
        if (response.isSuccessful && response.body() != null) {
            d("Main Activity", "Response: ${response.body()}")
            return Resource.Success(response.body()!!)
        } else {
            d("LoginActivity", "Login Failed")
            response.errorBody()?.let {
                e("LoginActivity", it.string())
            }
        }
        return Resource.Error("Login Failed")
    }

    override suspend fun getCourses(): Resource<List<Course>> {
        val response = try {
            d("Course Activity", "getting courses")
            var cookie = sharedPreferences.getString("COOKIE", "")
            cookie = "token=$cookie;"
            d("cookie", cookie.toString())
            retrofitApi.getCourses(cookie)
        } catch (e: IOException) {
            e("Main Activity", "IOEException, no internet?", e)
            return Resource.Error("Network Failure")
        } catch (e: HttpException) {
            e("Main Activity", "HTTP exception, unexpected response", e)
            return Resource.Error("Network Failure")
        }
        d("Main Activity", "Response: $response")
        if (response.isSuccessful && response.body() != null) {
            d("Main Activity", "Response: ${response.body()}")
            return Resource.Success(response.body()!!)
        } else {
            d("LoginActivity", "Login Failed")
            response.errorBody()?.let {
                e("LoginActivity", it.string())
            }
        }
        return Resource.Error("Login Failed")
    }

    override suspend fun getSessionsDetail(courseId: String): Resource<SessionsData> {
        val response = try {
            d("Sessions Activity", "getting sessions")
            var cookie = sharedPreferences.getString("COOKIE", "")
            cookie = "token=$cookie;"
            d("cookie", cookie.toString())
            retrofitApi.getSessions(cookie, courseId)
        } catch (e: IOException) {
            e("Session Activity", "IOEException, no internet?", e)
            return Resource.Error("Network Failure")
        } catch (e: HttpException) {
            e("Session Activity", "HTTP exception, unexpected response", e)
            return Resource.Error("Network Failure")
        }
        d("Session Activity", "Response: $response")
        if (response.isSuccessful && response.body() != null) {
            d("Session Activity", "Response: ${response.body()}")
            return Resource.Success(response.body()!!)
        } else {
            d("Session Activity", "Login Failed")
            response.errorBody()?.let {
                e("Session Activity", it.string())
            }
        }
        return Resource.Error("Login Failed")
    }

    override suspend fun updateProfile(user: UpdateProfile): Resource<User> {
        val response = try {
            d("Profile Activity", "updating profile")
            var cookie = sharedPreferences.getString("COOKIE", "")
            cookie = "token=$cookie;"
            retrofitApi.updateProfile(cookie, user)
        } catch (e: IOException) {
            e("Profile Activity", "IOEException, no internet?", e)
            return Resource.Error("Network Failure")
        } catch (e: HttpException) {
            e("Profile Activity", "HTTP exception, unexpected response", e)
            return Resource.Error("Network Failure")
        }

        return Resource.Success(response.body()!!)
    }
}