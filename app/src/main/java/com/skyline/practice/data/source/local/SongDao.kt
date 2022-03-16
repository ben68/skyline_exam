package com.skyline.practice.data.source.local

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
abstract class SongDao: BaseDao<Song>() {
    @Query("SELECT * FROM song")
    abstract fun getAll(): Single<List<Song>>

    @Query("SELECT * FROM song WHERE id = :id")
    abstract fun getSong(id: String): Single<Song>
}