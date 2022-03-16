package com.skyline.practice.data.bean

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    val artistId: String?,
    val artistName: String,
    val artistUrl: String?,
    val artworkUrl100: String,
    val collectionName: String?,
    val contentAdvisoryRating: String?,
    val copyright: String?,
    val genres: List<Genre>?,
    val id: String,
    val kind: String?,
    val name: String,
    val releaseDate: String?,
    val url: String?
)