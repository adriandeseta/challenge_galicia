package com.example.challengegalicia.data

import com.example.challengegalicia.data.response.ResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface UserListApiService {


    @GET("api/")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int = 20,
        @Query("seed") seed: String = "challenge"
    ): ResponseWrapper
}

