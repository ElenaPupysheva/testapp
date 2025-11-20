package com.alonso.testapp.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private const val BASE_URL = "https://sr111.05.testing.place/"
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val newRequest = original.newBuilder()
                    .addHeader("App-Token", "secret")
                    .addHeader(
                        "Authorization",
                        "Bearer pdhO16atBIXogpPzaLDjDcl5Gpmbz9Mdl1mjhrhWZBuOgNCgxDlk7mMIbFcEc7mj"
                    )
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}