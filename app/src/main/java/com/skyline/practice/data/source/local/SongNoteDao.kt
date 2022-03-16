package com.skyline.practice.data.source.local

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
abstract class SongNoteDao: BaseDao<SongNote>() {
    @Query("SELECT * FROM SongNote WHERE songId = :songId")
    abstract fun getNote(songId: String): Single<SongNote>
}