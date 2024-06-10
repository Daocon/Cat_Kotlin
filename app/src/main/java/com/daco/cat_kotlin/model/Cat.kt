package com.daco.cat_kotlin.model

import com.google.gson.annotations.SerializedName

data class Cat(
    @SerializedName("_id")
    val id: String,
    val tags: List<String>,
)
