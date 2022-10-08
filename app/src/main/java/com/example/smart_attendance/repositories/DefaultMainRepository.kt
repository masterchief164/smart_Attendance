package com.example.smart_attendance.repositories

import android.content.SharedPreferences
import android.util.Log.e
import com.example.smart_attendance.api.APIInterface
import com.example.smart_attendance.data.QRData
import com.example.smart_attendance.data.User
import com.example.smart_attendance.other.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Tasks.await
import com.google.gson.Gson
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
        } else
            Resource.Error("User not found")
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
}