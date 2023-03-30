package com.shaswat.smart_attendance.di

import android.content.SharedPreferences
import com.shaswat.smart_attendance.api.APIInterface
import com.shaswat.smart_attendance.repositories.AuthRepository
import com.shaswat.smart_attendance.repositories.DefaultAuthRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AuthModule {

    @Provides
    fun providesAuthRepository(retrofitApi: APIInterface, sharedPreferences: SharedPreferences, gson:Gson) =
        DefaultAuthRepository(retrofitApi, sharedPreferences, gson) as AuthRepository
}