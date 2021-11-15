package com.mnzlabz.guessthenumber.data.remote

import com.mnzlabz.guessthenumber.data.model.GTNModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IGTNService {
    @GET("/rand")
    suspend fun getRandomNumber(@Query("min") min: Int,
                                @Query("max") max: Int): Response<GTNModel>
}