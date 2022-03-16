package com.skyline.practice.data.bean

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Author(
    val name: String,
    val uri: String
)