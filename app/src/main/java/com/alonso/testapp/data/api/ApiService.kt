package com.alonso.testapp.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/v2/interval-timers/{id}")
    suspend fun getIntervalTimer(
        @Path("id") id: Long
    ): TimerResponseDto
}