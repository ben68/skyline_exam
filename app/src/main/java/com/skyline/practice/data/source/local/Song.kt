package com.skyline.practice.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(
    @PrimaryKey val id: String,
    val name: String,
    val artist: String,
    val imageUrl: String
) {
    companion object {
        val empty by lazy {
            Song(id = "", name = "", artist = "", imageUrl = "")
        }
    }
}