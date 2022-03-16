package com.skyline.practice.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongNote(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    var text: String? = null,
    var imageUri: String? = null,
    var videoUri: String? = null,
    val songId: String
) {
    companion object {
        val empty by lazy {
            SongNote(id = null, text = null, imageUri = null, videoUri = null, songId = "")
        }
    }
}
