package com.skyline.practice.data.bean

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Link(
    val alternate: String?,
    val self: String?
)