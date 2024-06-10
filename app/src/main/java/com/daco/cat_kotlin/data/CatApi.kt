package com.daco.cat_kotlin.data

import com.daco.cat_kotlin.model.Cat
import retrofit2.http.GET

interface CatApi {
    @GET("cats?tags=cute&skip=0&limit=10")
    suspend fun getCats(): List<Cat>
}