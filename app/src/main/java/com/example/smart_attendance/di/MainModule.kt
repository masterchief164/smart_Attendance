package com.example.smart_attendance.di

import android.content.SharedPreferences
import com.example.smart_attendance.api.APIInterface
import com.example.smart_attendance.repositories.DefaultMainRepository
import com.example.smart_attendance.repositories.MainRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object MainModule {

    @Provides
    fun providesMainRepository(
        retrofitApi: APIInterface,
        sharedPreferences: SharedPreferences,
        gson: Gson,
        googleSignInClient: GoogleSignInClient
    ) =
        DefaultMainRepository(
            retrofitApi,
            sharedPreferences,
            gson,
            googleSignInClient
        ) as MainRepository
}