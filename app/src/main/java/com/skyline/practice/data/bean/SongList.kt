package com.skyline.practice.data.bean

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SongList(
    val feed: Feed
) {
    companion object {
        val empty = SongList(
            feed = Feed(
                author = Author(
                    name = "",
                    uri = ""
                ),
                copyright = "",
                country = "",
                icon = "",
                id = "",
                links = listOf(),
                results = listOf(),
                title = "",
                updated = ""
            )
        )
    }
}