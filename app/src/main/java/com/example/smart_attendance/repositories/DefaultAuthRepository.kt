package com.example.smart_attendance.repositories

import android.content.SharedPreferences
import android.util.Log
import com.example.smart_attendance.api.APIInterface
import com.example.smart_attendance.data.LoginRequest
import com.example.smart_attendance.data.User
import com.example.smart_attendance.other.Resource
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val retrofitApi: APIInterface,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : AuthRepository {
    override suspend fun loginUser(idToken: String): Resource<User> {
        val response = try {
            Log.d("LoginActivity", "Authenticating with Google, idToken: $idToken")
            retrofitApi.postLogin(LoginRequest(idToken))
        } catch (e: IOException) {
            Log.e("LoginActivity", "IOEException, no internet?", e)
            return Resource.Error("Network Failure")
        } catch (e: HttpException) {
            Log.e("LoginActivity", "HTTP exception, unexpected response", e)
            return Resource.Error("Network Failure")
        }
        Log.d("LoginActivity", "Response: $response")
        if (response.isSuccessful && response.body() != null) {
            Log.d("LoginActivity", "Login Successful ${response.body()}")
            saveUser(response.body()!!)
            val cookie = response.headers()["Set-Cookie"]
            saveCookie(cookie!!)
            Log.d("LoginActivity", "Login Successful cookie ${response.headers()["Set-Cookie"]}")

            return Resource.Success(response.body()!!)
        } else {
            Log.d("LoginActivity", "Login Failed")
            response.errorBody()?.let {
                Log.e("LoginActivity", it.string())
            }
        }
        return Resource.Error("Login Failed")
    }

    override fun saveCookie(cookie: String) {
        sharedPreferences.edit().putString("COOKIE", cookie).apply()
    }

    override suspend fun saveUser(user: User) {
        sharedPreferences.edit().putString("USER", gson.toJson(user)).apply()
    }
}