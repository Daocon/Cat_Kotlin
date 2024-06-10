package com.daco.cat_kotlin.data

class CatRepository(private val catApi: CatApi) {
    suspend fun getCats() = catApi.getCats()
}