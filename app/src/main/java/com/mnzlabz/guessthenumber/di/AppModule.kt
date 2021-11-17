package com.mnzlabz.guessthenumber.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mnzlabz.guessthenumber.data.local.GTNDatabase
import com.mnzlabz.guessthenumber.data.local.GuessingDAO
import com.mnzlabz.guessthenumber.data.remote.IGTNService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL: String = "https://us-central1-ss-devops.cloudfunctions.net"

    var interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    var client = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()

    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun providesGTNService(retrofit: Retrofit) : IGTNService = retrofit.create(IGTNService::class.java)

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context) : GTNDatabase = GTNDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun providesDao(gtnDatabase: GTNDatabase) : GuessingDAO = gtnDatabase.guessingDao()
}