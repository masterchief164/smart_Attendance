package com.shaswat.smart_attendance.di

import android.content.Context
import android.content.SharedPreferences
import com.shaswat.smart_attendance.api.APIInterface
import com.shaswat.smart_attendance.other.UnsafeOkHttpClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton // This determines that only one instance of this context exists
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context) =
        context

    @Singleton
    @Provides
    fun provideGoogleSignInOptions() =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestServerAuthCode("197392019228-d0n763iqtt9hfoahuqmme50aqeu8ausm.apps.googleusercontent.com")
            .build()

    @Singleton
    @Provides
    fun provideGoogleSignInClient(@ApplicationContext context: Context, gso: GoogleSignInOptions) =
        GoogleSignIn.getClient(context, gso)

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun providesRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl("https://smartattendance.ddns.net")
//        .client(com.shaswat.smart_attendance.other.UnsafeOkHttpClient.getUnsafeOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesAPIInterface(retrofit: Retrofit): APIInterface = retrofit.create(APIInterface::class.java)

    @Singleton
    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("smart", Context.MODE_PRIVATE)
}