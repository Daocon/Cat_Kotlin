package com.daco.cat_kotlin.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class ApiService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://cataas.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
        )
        .build()
    val catApi: CatApi by lazy {
        retrofit.create(CatApi::class.java)
    }
//    https://cataas.com/api/cats?tags=cute&skip=0&limit=10
}