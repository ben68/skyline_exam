package com.skyline.practice.data.bean

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(
    val genreId: String,
    val name: String,
    val url: String
)